package com.anchuk.citylist.controller;

import com.anchuk.citylist.model.dto.auth.CustomUserDetails;
import com.anchuk.citylist.model.dto.auth.LoginRequest;
import com.anchuk.citylist.model.dto.auth.LoginResponse;
import com.anchuk.citylist.service.CustomUsrDetailsService;
import com.anchuk.citylist.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/auth", produces = "application/json")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final CustomUsrDetailsService usrDetailsService;

    private final static String TOKEN_TYPE = "Bearer";

    public AuthController(TokenService tokenService,
                          AuthenticationManager authManager,
                          CustomUsrDetailsService usrDetailsService) {
        super();
        this.tokenService = tokenService;
        this.authManager = authManager;
        this.usrDetailsService = usrDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication auth = authManager.authenticate(authenticationToken);
        CustomUserDetails user = (CustomUserDetails) usrDetailsService.loadUserByUsername(request.getUsername());
        Jwt accessToken = tokenService.generateAccessToken(user);
        Jwt refreshToken = tokenService.generateRefreshToken(user);
        user.getAuthorities();

        return ResponseEntity.ok(LoginResponse.builder()
                .accessToken(accessToken.getTokenValue())
                .tokenType(TOKEN_TYPE)
                .expiresIn(accessToken.getExpiresAt())
                .refreshToken(refreshToken.getTokenValue())
                .scope(tokenService.getScope(user))
                .build());
    }


    @GetMapping("/token/refresh")
    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String oldRefreshToken = headerAuth.substring(7);

        String login = tokenService.parseToken(oldRefreshToken);
        CustomUserDetails user = (CustomUserDetails) usrDetailsService.loadUserByUsername(login);
        Jwt accessToken = tokenService.generateAccessToken(user);
        Jwt refreshToken = tokenService.generateRefreshToken(user);

        return ResponseEntity.ok(LoginResponse.builder()
                .accessToken(accessToken.getTokenValue())
                .tokenType(TOKEN_TYPE)
                .expiresIn(accessToken.getExpiresAt())
                .refreshToken(refreshToken.getTokenValue())
                .scope(tokenService.getScope(user))
                .build());
    }
}
