package com.personalplugsite.apicore.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personalplugsite.apicore.config.JwtTokenUtil;
import com.personalplugsite.data.dtos.AuthenticaitonResponceDto;
import com.personalplugsite.data.dtos.AuthenticationRequestDto;
import com.personalplugsite.data.dtos.RegisterRequestDto;
import com.personalplugsite.data.entities.User;
import com.personalplugsite.data.enums.Role;
import com.personalplugsite.data.exception.UserCreationException;
import com.personalplugsite.data.repos.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepo userRepo;
        private final PasswordEncoder passwordEncoder;
        private final JwtTokenUtil jwtTokenUtil;
        private final AuthenticationManager authenticationManager;

        public AuthenticaitonResponceDto register(RegisterRequestDto request) {
                if (userRepo.findByEmail(request.getEmail()).isPresent()) {
                        throw new UserCreationException("User already exists");
                }
                User user = User.builder()
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build();
                if (user == null) {
                        throw new UserCreationException("Error creating user");
                }
                userRepo.save(user);
                String jwtToken = jwtTokenUtil.generateToken(user);
                return AuthenticaitonResponceDto.builder()
                                .token(jwtToken)
                                .build();
        }

        public AuthenticaitonResponceDto authenticate(AuthenticationRequestDto request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = userRepo.findByEmail(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                jwtTokenUtil.removeUserTokensFromBlacklist(user.getId());
                var jwtToken = jwtTokenUtil.generateToken(user);
                return AuthenticaitonResponceDto.builder()
                                .token(jwtToken)
                                .build();
        }

}
