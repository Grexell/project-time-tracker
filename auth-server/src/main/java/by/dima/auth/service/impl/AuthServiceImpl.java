package by.dima.auth.service.impl;

import by.dima.auth.dto.RefreshToken;
import by.dima.auth.dto.Token;
import by.dima.auth.dto.TokenRequest;
import by.dima.model.User;
import by.dima.auth.service.AuthService;
import by.dima.auth.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {
    private static final long ACCESS_EXPIRATION_TIME = 864_000_00;
    private static final long REFRESH_EXPIRATION_TIME = 864_000_000;

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<Token> createToken(TokenRequest tokenRequest) {
        return userService.findByUsernameAndPassword(tokenRequest.getUsername(), tokenRequest.getPassword())
                .switchIfEmpty(Mono.error(IllegalArgumentException::new))
                .map(user -> generateTokenForUser(user, tokenRequest.getClientSecret()));
    }

    @Override
    public Mono<Token> refreshToken(RefreshToken token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(token.getClientSecret()))
                .build()
                .verify(token.getRefreshToken());
        return userService.findById(jwt.getClaim("userId").asLong())
                .map(user -> generateTokenForUser(user, token.getClientSecret()));


    }

    private Token generateTokenForUser(User user, String clientSecret) {
        Algorithm algorithm = Algorithm.HMAC512(clientSecret);
        String accessToken = JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("user", user.getEmail())
                .withClaim("role", user.getRole().getName())
                .withExpiresAt(Date.from(Instant.now().plusMillis(ACCESS_EXPIRATION_TIME))).sign(algorithm);
        String refreshToken = JWT.create()
                .withClaim("userId", user.getId())
                .withExpiresAt(Date.from(Instant.now().plusMillis(REFRESH_EXPIRATION_TIME))).sign(algorithm);
        return new Token(accessToken, refreshToken);
    }
}
