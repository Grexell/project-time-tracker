package by.dima.employee.service.impl;

import by.dima.employee.service.ProjectService;
import by.dima.model.Project;
import by.dima.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public Flux<Project> getProjects(Long userId) {
        return null;
    }

    @Override
    public Flux<Task> getTasks(Long userId, Long projectId) {
        return null;
    }

    @Override
    public Mono<Task> createTask(Long userId, Task task) {
        return null;
    }

    @Override
    public Mono<Task> updateTask(Long userId, Task task) {
        return null;
    }
}
