package by.dima.auth.dao;

import by.dima.model.Role;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRoleDao {
    Mono<Boolean> createUserRoles(Long userId, List<Role> roles);
    Mono<Boolean> deleteUserRoles(Long userId);
}
