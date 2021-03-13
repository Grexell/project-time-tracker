package by.dima.auth.service.impl;

import by.dima.auth.dao.RoleDao;
import by.dima.auth.dao.UserDao;
import by.dima.auth.dao.UserRoleDao;
import by.dima.model.User;
import by.dima.auth.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Mono<User> findById(Long id) {
        return loadUserRoles(userDao.findById(id));
    }

    @Override
    public Mono<User> findByUsernameAndPassword(String username, String password) {
        return loadUserRoles(userDao.findByUsername(username))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Override
    public Mono<User> create(User user) {
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user)
                .flatMap(savedUser -> userRoleDao.createUserRoles(savedUser.getId(), user.getRoles()).thenReturn(savedUser));
    }

    @Override
    public Mono<User> updatePassword(User user) {
        return userDao.findById(user.getId())
                .doOnNext(savedUser -> savedUser.setPassword(passwordEncoder.encode(user.getPassword())))
                .flatMap(userDao::save);
    }

    @Override
    public Mono<User> updateRoles(User user) {
        return userDao.findById(user.getId())
                .flatMap(savedUser -> userRoleDao.deleteUserRoles(savedUser.getId())
                        .flatMap(res -> userRoleDao.createUserRoles(savedUser.getId(), user.getRoles()))
                        .thenReturn(savedUser));
    }

    @Override
    public Flux<User> findAll() {
        return userDao.findAll().doOnNext(user -> user.setPassword(null));
    }

    private Mono<User> loadUserRoles(Mono<User> userMono) {
        return userMono.flatMap(user -> roleDao.findAllForUser(user.getId())
                .collectList()
                .map(roles -> {
                    user.setRoles(roles);
                    return user;
                }));
    }
}
