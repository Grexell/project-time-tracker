package by.dima.auth.service;

import by.dima.auth.dto.RefreshToken;
import by.dima.auth.dto.Token;
import by.dima.auth.dto.TokenRequest;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<Token> createToken(TokenRequest tokenRequest);
    Mono<Token> refreshToken(RefreshToken refreshToken);
}
