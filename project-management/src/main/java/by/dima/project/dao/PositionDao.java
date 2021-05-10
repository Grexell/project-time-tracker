package by.dima.project.dao;

import by.dima.model.Position;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PositionDao extends ReactiveCrudRepository<Position, Long> {
    @Query("CALL change_position(:#{#managerId} , :#{#userId}, :#{#position.id})")
    Mono<Void> changePosition(Long managerId, Long userId, Position position);
}
