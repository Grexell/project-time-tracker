package by.dima.auth.dao;

import by.dima.auth.model.Principal;
import by.dima.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveCrudRepository<Principal, Long> {
    Mono<Principal> findByUsername(String username);
}
