package by.dima.project.controller;

import by.dima.model.Project;
import by.dima.model.User;
import by.dima.project.dto.ProjectDetailsDto;
import by.dima.project.model.ProjectDetails;
import by.dima.project.service.ProjectService;
import by.dima.utils.TokenUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static by.dima.utils.TokenUtils.MANAGER_ROLE;
import static by.dima.utils.TokenUtils.is;
import static org.springframework.http.HttpHeaders.*;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Flux<ProjectDetailsDto>> getProjects(@RequestHeader(AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(projectService.getProjects(user.getId()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Mono<Project>> createProject(@RequestBody ProjectDetails project, @RequestHeader(AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(projectService.createProject(user.getId(), project));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("{projectId}/attach")
    public ResponseEntity<Mono<Void>> attachProject(@PathVariable Long projectId, @RequestHeader(AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(projectService.attachProject(user.getId(), projectId));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("{projectId}/finish")
    public ResponseEntity<Mono<Void>> finishProject(@PathVariable Long projectId,
                                                    @RequestParam("finishDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishDate,
                                                    @RequestHeader(AUTHORIZATION) String authHeader) {
        User user = TokenUtils.extractUser(authHeader);
        if (is(user, MANAGER_ROLE)) {
            return ResponseEntity.ok(projectService.finishProject(user.getId(), projectId, finishDate));
        }
        return ResponseEntity.badRequest().build();
    }
}
