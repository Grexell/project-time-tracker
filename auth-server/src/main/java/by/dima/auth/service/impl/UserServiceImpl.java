package by.dima.auth.service.impl;

import by.dima.auth.dao.RoleDao;
import by.dima.auth.dao.UserDao;
import by.dima.auth.model.Principal;
import by.dima.auth.service.UserService;
import by.dima.model.Calendar;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Mono<Principal> findById(Long id) {
        return loadUserRoles(userDao.findById(id));
    }

    @Override
    public Mono<Principal> findByUsernameAndPassword(String username, String password) {
        return loadUserRoles(userDao.findByEmail(username))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Override
    public Mono<Principal> create(Principal user) {
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleId(user.getRole().getId());
        user.setCalendarId(user.getCalendar().getId());
        return userDao.save(user).doOnNext(usr -> usr.setPassword(null));
    }

    @Override
    public Mono<Principal> update(Principal user) {
        if (user.getId() != null) {
            return userDao.findById(user.getId())
                    .doOnNext(principal -> {
                        principal.setEmail(user.getEmail());
                        principal.setFirstName(user.getFirstName());
                        principal.setSecondName(user.getSecondName());
                        principal.setCalendarId(user.getCalendar().getId());
                        String newPassword = user.getPassword();
                        if (newPassword != null && !newPassword.isEmpty()) {
                            principal.setPassword(passwordEncoder.encode(newPassword));
                        }
                        principal.setRoleId(user.getRole().getId());
                    })
                    .flatMap(userDao::save)
                    .doOnNext(usr -> usr.setPassword(null));
        }
        return Mono.error(new IllegalArgumentException());
    }

    @Override
    public Mono<Void> delete(long userId) {
        return userDao.deleteById(userId);
    }

//    @Override
//    public Mono<User> updatePassword(User user) {
//        return userDao.findById(user.getId())
//                .doOnNext(savedUser -> savedUser.setPassword(passwordEncoder.encode(user.getPassword())))
//                .flatMap(userDao::save);
//    }
//
//    @Override
//    public Mono<User> updateRoles(User user) {
//        return userDao.findById(user.getId())
//                .flatMap(savedUser -> userRoleDao.deleteUserRoles(savedUser.getId())
//                        .flatMap(res -> userRoleDao.createUserRoles(savedUser.getId(), user.getRole()))
//                        .thenReturn(savedUser));
//    }

    @Override
    public Flux<Principal> findAll() {
        return userDao.findAll()
                .flatMap(principal -> loadUserRoles(Mono.just(principal)))
                .doOnNext(this::loadUserCalendar)
                .doOnNext(user -> user.setPassword(null));
    }

    private Mono<Principal> loadUserRoles(Mono<Principal> userMono) {
        return userMono.flatMap(user -> roleDao.findById(user.getRoleId()).doOnNext(user::setRole).thenReturn(user));
    }

    private Principal loadUserCalendar(Principal user) {
        user.setCalendar(new Calendar(user.getCalendarId()));
        return user;
    }
}
