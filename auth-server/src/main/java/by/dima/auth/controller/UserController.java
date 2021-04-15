package by.dima.auth.controller;

import by.dima.auth.model.Principal;
import by.dima.auth.service.UserService;
import by.dima.utils.TokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static by.dima.utils.TokenUtils.ADMIN_ROLE;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Flux<Principal>> getUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(userService.findAll());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Mono<Principal>> createUser(@RequestBody Principal user, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(userService.create(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<Mono<Principal>> updateUser(@RequestBody Principal user, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(userService.update(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Mono<Void>> deleteUser(@PathVariable long userId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (TokenUtils.is(authHeader, ADMIN_ROLE)) {
            return ResponseEntity.ok(userService.delete(userId));
        }
        return ResponseEntity.badRequest().build();
    }
}
