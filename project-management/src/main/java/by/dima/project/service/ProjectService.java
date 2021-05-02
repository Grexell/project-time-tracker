package by.dima.project.service;

import by.dima.model.Project;
import by.dima.project.dto.ProjectDetailsDto;
import by.dima.project.model.ProjectDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ProjectService {
    Flux<ProjectDetailsDto> getProjects(Long userId);
    Mono<Project> createProject(Long userId, ProjectDetails project);
    Mono<Void> attachProject(Long userId, Long projectId);
    Mono<Void> finishProject(Long userId, Long projectId, LocalDate finishDate);
}
