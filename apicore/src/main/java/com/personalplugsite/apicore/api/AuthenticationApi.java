package com.personalplugsite.apicore.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personalplugsite.apicore.config.JwtTokenUtil;
import com.personalplugsite.apicore.service.AuthenticationService;
import com.personalplugsite.data.dtos.AuthenticaitonResponceDto;
import com.personalplugsite.data.dtos.AuthenticationRequestDto;
import com.personalplugsite.data.dtos.RegisterRequestDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthenticationApi {
    private final AuthenticationService authenticationService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "register")
    public ResponseEntity<AuthenticaitonResponceDto> register(
            @RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(value = "authenticate")
    public ResponseEntity<AuthenticaitonResponceDto> authenticate(
            @RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        jwtTokenUtil.addTokenToBlacklist(token);
        return ResponseEntity.ok().build();
    }
}
