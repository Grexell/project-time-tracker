package by.dima.auth.dao;

import by.dima.model.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RoleDao extends ReactiveCrudRepository<Role, Long> {
}
