package by.dima.auth.dao;

import by.dima.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByUsername(String username);
}
