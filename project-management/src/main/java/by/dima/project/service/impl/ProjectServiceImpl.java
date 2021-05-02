package by.dima.project.service.impl;

import by.dima.model.Project;
import by.dima.project.dao.ProjectCustomerDao;
import by.dima.project.dao.ProjectDao;
import by.dima.project.dao.SalaryDao;
import by.dima.project.dao.TeamDao;
import by.dima.project.model.ProjectCustomer;
import by.dima.project.model.ProjectDetails;
import by.dima.project.model.TeamMember;
import by.dima.project.service.ProjectService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
    public Flux<ProjectDetails> getProjects(Long userId) {
        return null;
    }

    @Override
    public Mono<Project> createProject(Long userId, ProjectDetails project) {
        return projectDao.create(userId, project).flatMap(projectId -> {
            project.setId(projectId);

            List<TeamMember> team = project.getTeam();
            team.forEach(member -> member.setProjectId(projectId));

            List<ProjectCustomer> customers = project.getCustomers();
            customers.forEach(customer -> customer.setProjectId(projectId));

            return Mono.zip(teamDao.saveAll(team)
                    .flatMap(member -> salaryDao.increaseSalary(userId, member.getUserId(), member.getProjectId(), member.getSalary(), member.isMonthly()))
                    .then(), customerDao.saveAll(customers).then())
                    .thenReturn(project);
        });
    }

    @Override
    public Mono<Void> attachProject(Long userId, Long projectId) {
//        todo find mot managed project before attach
        return projectDao.attach(userId, projectId);
    }
}
