package by.dima.auth.dao;

import by.dima.model.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RoleDao extends ReactiveCrudRepository<Role, Long> {
    @Query("SELECT r.* FROM \"role\" as r INNER JOIN user_role ON r.id = user_role.role_id WHERE user_role.user_id=:userId")
    Flux<Role> findAllForUser(Long userId);
}
