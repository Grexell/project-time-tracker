package by.dima.employee.service;

import by.dima.model.Project;
import by.dima.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
    Flux<Project> getProjects(Long userId);
    Flux<Task> getTasks(Long userId, Long projectId);
    Mono<Task> createTask(Long userId, Task task);
    Mono<Task> updateTask(Long userId, Task task);
}
