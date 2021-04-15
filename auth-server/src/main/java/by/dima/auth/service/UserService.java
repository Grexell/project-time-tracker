package by.dima.auth.service;

import by.dima.auth.model.Principal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Principal> findById(Long id);
    Mono<Principal> findByUsernameAndPassword(String username, String password);

    Mono<Principal> create(Principal user);
    Mono<Principal> update(Principal user);
    Mono<Void> delete(long userId);

//    Mono<Principal> updatePassword(Principal user);

//    Mono<User> updateRoles(User user);

    Flux<Principal> findAll();
}
