package by.dima.auth.controller;

import by.dima.auth.dao.UserDao;
import by.dima.model.User;
import by.dima.auth.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserDao userDao;

    public UserController(UserService userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
    }

    @GetMapping
    public Flux<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return userService.create(user);
    }

    @GetMapping("{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
}
