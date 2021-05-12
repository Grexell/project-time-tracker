package by.dima.employee.dao;

import by.dima.model.Task;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskDao extends ReactiveCrudRepository<Task, Long> {
    @Query("SELECT * FROM task t where exists(select * from user_project up where up.id = t.user_id and up.user_id =:#{#userId} and up.project_id =:#{#projectId})")
    Flux<Task> findAllByUserAndProject(long userId, long projectId);
    @Query("SELECT * FROM task t where exists(select * from user_project up where up.id = t.user_id and up.user_id =:#{#userId}) and t.id = :#{#id}")
    Mono<Task> findByUserAndId(long userId, long id);
}
