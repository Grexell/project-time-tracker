package by.dima.employee.dao;

import by.dima.model.Project;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProjectDao extends ReactiveCrudRepository<Project, Long> {
    @Query("SELECT * FROM project p where exists(select 1 from user_project where project_id = p.id user_id =:#{#userId})")
    Flux<Project> findAllByUserId(long userId);
}
