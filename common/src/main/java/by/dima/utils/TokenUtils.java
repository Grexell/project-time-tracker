package by.dima.utils;

import by.dima.model.Role;
import by.dima.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.stream.Collectors;

public class TokenUtils {
    public static final String ADMIN_ROLE = "admin";
    public static final String MANGER_ROLE = "manager";
    public static final String USER_ROLE = "user";

    public static User extractUser(String authHeader) {
        User user = new User();
        DecodedJWT jwt = JWT.decode(authHeader);
        user.setId(jwt.getClaim("userId").asLong());
        user.setUsername(jwt.getClaim("username").asString());
        user.setRoles(jwt.getClaim("roles")
                .asList(String.class)
                .stream()
                .map(Role::new)
                .collect(Collectors.toList()));
        return user;
    }

    public static boolean is(User user, String role) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .anyMatch(role::equals);
    }
}
