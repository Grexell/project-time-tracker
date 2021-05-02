package by.dima.project.service;

import by.dima.model.Project;
import by.dima.project.model.ProjectDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
    Flux<ProjectDetails> getProjects(Long userId);
    Mono<Project> createProject(Long userId, ProjectDetails project);
    Mono<Void> attachProject(Long userId, Long projectId);
}
