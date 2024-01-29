package com.personalplugsite.apicore.service;

import com.personalplugsite.apicore.config.JwtTokenUtil;
import com.personalplugsite.data.dtos.AuthenticaitonResponceDto;
import com.personalplugsite.data.dtos.AuthenticationRequestDto;
import com.personalplugsite.data.dtos.RegisterRequestDto;
import com.personalplugsite.data.dtos.UserDto;
import com.personalplugsite.data.entities.User;
import com.personalplugsite.data.enums.Role;
import com.personalplugsite.data.exception.UserCreationException;
import com.personalplugsite.data.repos.UserRepo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    User user = User
      .builder()
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
    return AuthenticaitonResponceDto
      .builder()
      .token(jwtToken)
      .userDto(getUserDtoFromUser(user))
      .build();
  }

  public AuthenticaitonResponceDto authenticate(
    AuthenticationRequestDto request
  ) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      )
    );
    var user = userRepo
      .findByEmail(request.getEmail())
      .orElseThrow(() -> new RuntimeException("User not found"));
    Integer userId = user.getId();
    if (userId != null) {
      jwtTokenUtil.removeUserTokensFromBlacklist(userId);
    }
    var jwtToken = jwtTokenUtil.generateToken(user);
    return AuthenticaitonResponceDto
      .builder()
      .token(jwtToken)
      .userDto(getUserDtoFromUser(user))
      .build();
  }

  private UserDto getUserDtoFromUser(User user) {
    return UserDto
      .builder()
      .id(user.getId())
      .firstName(user.getFirstName())
      .lastName(user.getLastName())
      .email(user.getEmail())
      .role(user.getRole().name())
      .build();
  }

  public AuthenticaitonResponceDto refreshSession(String token) {
    String email = jwtTokenUtil.extractUsername(token);
    Optional<User> optionalUser = userRepo.findByEmail(email);
    if (optionalUser.isPresent()) {
      return AuthenticaitonResponceDto
        .builder()
        .token(token)
        .userDto(getUserDtoFromUser(optionalUser.get()))
        .build();
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  }
}
