package com.personalplugsite.apicore.api;

import com.personalplugsite.apicore.config.JwtTokenUtil;
import com.personalplugsite.apicore.service.AuthenticationService;
import com.personalplugsite.data.dtos.AuthenticaitonResponceDto;
import com.personalplugsite.data.dtos.AuthenticationRequestDto;
import com.personalplugsite.data.dtos.RegisterRequestDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationApi {

  private final AuthenticationService authenticationService;
  private final JwtTokenUtil jwtTokenUtil;

  @Value("${pps-app.jwt.cookieName}")
  private String jwtCookieName;

  @Value("${pps-app.jwt.cookieMaxAge}")
  private int jwtCookieMaxAge;

  @PostMapping(value = "/user/register")
  public ResponseEntity<AuthenticaitonResponceDto> userRegister(
    @RequestBody RegisterRequestDto request,
    HttpServletRequest httpRequest,
    HttpServletResponse response
  ) {
    handleExistingToken(httpRequest);
    AuthenticaitonResponceDto authResponse = authenticationService.register(
      request
    );
    String jwtToken = authResponse.getToken();
    response.addCookie(generateCookie(jwtToken));
    log.info("User registered: {}", authResponse.getUser().getEmail());
    return ResponseEntity.ok(authResponse);
  }

  @PostMapping(value = "/user/authenticate")
  public ResponseEntity<AuthenticaitonResponceDto> userAuthenticate(
    @RequestBody AuthenticationRequestDto request,
    HttpServletRequest httpRequest,
    HttpServletResponse response
  ) {
    handleExistingToken(httpRequest);
    AuthenticaitonResponceDto authResponse = authenticationService.authenticate(
      request
    );
    String jwtToken = authResponse.getToken();
    response.addCookie(generateCookie(jwtToken));
    log.info("User authenticated: {}", authResponse.getUser().getEmail());
    return ResponseEntity.ok(authResponse);
  }

  @PostMapping(value = "/user/logout")
  public ResponseEntity<Void> userLogout(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    String token = jwtTokenUtil.extractTokenFromRequest(request);
    jwtTokenUtil.addTokenToBlacklist(token);
    response.addCookie(generateCookie(null));
    log.info("User logged out");
    return ResponseEntity.ok().build();
  }

  /**
   * Generates a cookie with the jwt token
   *
   * @param jwtToken
   * @return Cookie
   */
  private Cookie generateCookie(String jwtToken) {
    Cookie jwtCookie = new Cookie(jwtCookieName, jwtToken);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(jwtCookieMaxAge); // sets max age to 7 days
    return jwtCookie;
  }

  /**
   * Handles an existing token by adding it to the blacklist.
   * Used on login and register to invalidate any existing tokens
   *
   * @param request
   */
  private void handleExistingToken(HttpServletRequest request) {
    String token = jwtTokenUtil.extractTokenFromRequest(request);
    if (token != null && !token.isEmpty()) {
      jwtTokenUtil.addTokenToBlacklist(token);
    }
  }
}
