package by.dima.employee.controller;

import by.dima.employee.service.ProjectService;
import by.dima.model.Project;
import by.dima.model.Task;
import by.dima.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static by.dima.utils.TokenUtils.extractUser;

@RestController
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Flux<Project> getProjects(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = extractUser(authHeader);
        return projectService.getProjects(user.getId());
    }

    @GetMapping("{projectId}/task")
    public Flux<Task> getTasks(@PathVariable Long projectId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = extractUser(authHeader);
        return projectService.getTasks(user.getId(), projectId);
    }

    @PostMapping("{projectId}/task")
    public Mono<Task> createTask(@PathVariable Long projectId, @RequestBody Task task, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = extractUser(authHeader);
        task.setProjectId(projectId);
        task.setId(null);
        return projectService.createTask(user.getId(), task);
    }

    @PutMapping("{projectId}/task")
    public Mono<Task> updateTask(@PathVariable Long projectId, @RequestBody Task task, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        User user = extractUser(authHeader);
        task.setProjectId(projectId);
        return projectService.updateTask(user.getId(), task);
    }
}
