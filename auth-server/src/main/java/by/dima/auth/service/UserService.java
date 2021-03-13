package by.dima.auth.service;

import by.dima.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findById(Long id);
    Mono<User> findByUsernameAndPassword(String username, String password);

    Mono<User> create(User user);
    Mono<User> updatePassword(User user);
    Mono<User> updateRoles(User user);

    Flux<User> findAll();
}
