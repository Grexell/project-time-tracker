package by.dima.project.service.impl;

import by.dima.model.Project;
import by.dima.project.dao.ProjectCustomerDao;
import by.dima.project.dao.ProjectDao;
import by.dima.project.dao.SalaryDao;
import by.dima.project.dao.TeamDao;
import by.dima.project.dto.ProjectDetailsDto;
import by.dima.project.model.ProjectCustomer;
import by.dima.project.model.ProjectDetails;
import by.dima.project.model.TeamMember;
import by.dima.project.service.ProjectService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;
    private final TeamDao teamDao;
    private final SalaryDao salaryDao;
    private final ProjectCustomerDao customerDao;

    public ProjectServiceImpl(ProjectDao projectDao, TeamDao teamDao, SalaryDao salaryDao, ProjectCustomerDao customerDao) {
        this.projectDao = projectDao;
        this.teamDao = teamDao;
        this.salaryDao = salaryDao;
        this.customerDao = customerDao;
    }

    @Override
    public Flux<ProjectDetailsDto> getProjects(Long userId) {
        return projectDao.findAll(userId).flatMap(project -> Mono.zip(teamDao.findAllByProjectId(project.getId())
                            .collectList(),
                    customerDao.findAllByProjectId(project.getId())
                            .collectList()).map(tuple -> {
            project.setTeam(tuple.getT1());
            project.setCustomers(tuple.getT2());
            return project;
        }));
    }

    @Override
    public Mono<Project> createProject(Long userId, ProjectDetails project) {
        return projectDao.create(userId, project).flatMap(projectId -> {
            project.setId(projectId);
            project.setAttached(true);

            List<? extends TeamMember> team = project.getTeam();
            team.forEach(member -> member.setProjectId(projectId));

            List<ProjectCustomer> customers = project.getCustomers();
            customers.forEach(customer -> customer.setProjectId(projectId));

            return Mono.zip(teamDao.saveAll(team)
                    .flatMap(member -> salaryDao.increaseSalary(userId, member.getUserId(), member.getProjectId(), member.getSalary(), member.isMonthly()))
                    .then(), customerDao.saveAll(customers))
                    .thenReturn(project);
        });
    }

    @Override
    public Mono<Project> updateProject(Long userId, ProjectDetails project) {
        return  projectDao.save(project).flatMap(projectId -> {
            project.setAttached(true);

            List<? extends TeamMember> team = project.getTeam();
            team.forEach(member -> member.setProjectId(projectId.getId()));

            List<ProjectCustomer> customers = project.getCustomers();
            customers.forEach(customer -> customer.setProjectId(projectId.getId()));

            return Mono.zip(teamDao.saveAll(team)
                    .flatMap(member -> salaryDao.increaseSalary(userId, member.getUserId(), member.getProjectId(), member.getSalary(), member.isMonthly()))
                    .then(teamDao.deleteAllByIdNotInAndProjectId(team.stream().map(TeamMember::getId).collect(Collectors.toList()), projectId.getId()).then())
                    .then(),
                    customerDao.deleteAll(projectId.getId()).then(customerDao.saveAll(customers)))
                    .thenReturn(project);
        });
    }

    @Override
    public Mono<Void> attachProject(Long userId, Long projectId) {
//        todo find not managed project before attach
        return projectDao.attach(userId, projectId);
    }

    @Override
    public Mono<Void> finishProject(Long userId, Long projectId, LocalDate finishDate) {
//        todo add finishing on specified date
        return projectDao.finish(userId, projectId);
    }
}
