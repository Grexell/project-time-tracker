package by.dima.project.dao;

import by.dima.model.Bonus;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BonusDao extends ReactiveCrudRepository<Bonus, Long> {
    @Query("call add_bonus(:#{manager_id}, :#{#user}, :#{#projectId}, :#{bonus.amount})")
    Mono<Void> addBonus(Long managerId, Long userId, long projectId, Bonus bonus);
}
