package by.dima.auth.service;

import by.dima.auth.model.Principal;
import by.dima.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Principal> findById(Long id);
    Mono<Principal> findByUsernameAndPassword(String username, String password);

    Mono<Principal> create(Principal user);
    Mono<Principal> update(Principal user);
//    Mono<Principal> updatePassword(Principal user);

//    Mono<User> updateRoles(User user);

    Flux<Principal> findAll();
}
