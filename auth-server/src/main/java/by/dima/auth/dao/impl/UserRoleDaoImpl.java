package by.dima.auth.dao.impl;

import by.dima.auth.dao.UserRoleDao;
import by.dima.model.Role;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {
    private final DatabaseClient client;

    public UserRoleDaoImpl(DatabaseClient client) {
        this.client = client;
    }

    @Override
    public Mono<Boolean> createUserRoles(Long userId, List<Role> roles) {
        return client.sql("insert into user_role(user_id, role_id) values :roles")
                .bind("roles", roles.stream()
                        .map(role -> new Object[]{ userId, role.getId() })
                        .collect(Collectors.toList()))
                .then()
                .hasElement();
    }

    @Override
    public Mono<Boolean> deleteUserRoles(Long userId) {
        return client.sql("delete from user_role where user_id=:userId")
                .bind("userId", userId).then().hasElement();
    }
}
