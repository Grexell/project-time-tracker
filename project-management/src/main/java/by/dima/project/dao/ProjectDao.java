package by.dima.project.dao;

import by.dima.project.model.ProjectDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProjectDao extends ReactiveCrudRepository<ProjectDetails, Long> {
    @Query("CALL createProject()")
    Mono<Void> create();
}
