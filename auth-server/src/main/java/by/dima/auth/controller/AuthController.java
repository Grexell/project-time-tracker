package by.dima.auth.controller;

import by.dima.auth.dto.RefreshToken;
import by.dima.auth.dto.Token;
import by.dima.auth.dto.TokenRequest;
import by.dima.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/token")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public Mono<Token> createToken(@RequestBody TokenRequest tokenRequest) {
        return authService.createToken(tokenRequest);
    }

    @PostMapping("/refresh")
    public Mono<Token> refreshToken(@RequestBody RefreshToken refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
