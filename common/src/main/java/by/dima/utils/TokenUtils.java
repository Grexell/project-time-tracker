package by.dima.utils;

import by.dima.model.Role;
import by.dima.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtils {
    public static final String ADMIN_ROLE = "admin";
    public static final String MANGER_ROLE = "manager";
    public static final String USER_ROLE = "user";

    public static User extractUser(String authHeader) {
        User user = new User();
        DecodedJWT jwt = JWT.decode(authHeader);
        user.setId(jwt.getClaim("userId").asLong());
        user.setEmail(jwt.getClaim("username").asString());
        user.setRole(new Role(jwt.getClaim("role").asString()));
        return user;
    }

    public static boolean is(String userToken, String role) {
        return is(extractUser(userToken), role);
    }

    public static boolean is(User user, String role) {
        return user.getRole().getName().equalsIgnoreCase(role);
    }
}
