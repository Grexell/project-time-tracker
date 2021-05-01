package by.dima.project.service.impl;

import by.dima.model.Project;
import by.dima.project.dao.ProjectDao;
import by.dima.project.model.ProjectDetails;
import by.dima.project.service.ProjectService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Flux<ProjectDetails> getProjects(Long userId) {
        return null;
    }

    @Override
    public Mono<Project> createProject(Project project) {
//        todo add procedure call with creating project
//        todo add queries for creating team and customers
        return null;
    }

    @Override
    public Mono<Void> attachProject(Long projectId, Long userId) {
//        todo add procedure call for attaching manager
        return null;
    }
}
