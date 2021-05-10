package by.dima.project.dao;

import by.dima.project.dto.ProjectDetailsDto;
import by.dima.project.model.ProjectDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectDao extends ReactiveCrudRepository<ProjectDetails, Long> {
    @Query("SELECT *, IF(manager_id = :#{#managerId}, 'True', 'False') AS attached  FROM manager_projects WHERE manager_id = :#{#managerId} order by start_date desc")
    Flux<ProjectDetailsDto> findAll(Long managerId);
    @Query("CALL create_project(:#{#managerId}, :#{#project.name}, :#{#project.startDate}, :#{#project.budget}, @project_id); SELECT @project_id")
    Mono<Long> create(Long managerId, ProjectDetails project);
    @Query("CALL attach_project(:#{#managerId}, :#{#projectId})")
    Mono<Void> attach(Long managerId, Long projectId);
    @Query("CALL finish_project(:#{#projectId}, :#{#managerId})")
    Mono<Void> finish(Long managerId, Long projectId);
}
