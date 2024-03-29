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
import org.springframework.web.bind.annotation.GetMapping;
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

  @Value("${pps-app.jwt.dayCountOfAuthValitiy}")
  private int jwtDayCountOfAuthValitiy;

  @PostMapping(value = "/user/register")
  public ResponseEntity<AuthenticaitonResponceDto> userRegister(
    @RequestBody RegisterRequestDto request,
    HttpServletRequest httpRequest,
    HttpServletResponse response
  ) {
    AuthenticaitonResponceDto authResponse = authenticationService.register(
      request
    );
    String jwtToken = authResponse.getToken();
    handleExistingToken(httpRequest, jwtToken);
    response.addCookie(generateCookie(jwtToken));
    log.info("User registered: {}", authResponse.getUserDto().getEmail());
    return ResponseEntity.ok(authResponse);
  }

  @PostMapping(value = "/user/authenticate")
  public ResponseEntity<AuthenticaitonResponceDto> userAuthenticate(
    @RequestBody AuthenticationRequestDto request,
    HttpServletRequest httpRequest,
    HttpServletResponse response
  ) {
    AuthenticaitonResponceDto authResponse = authenticationService.authenticate(
      request
    );
    String jwtToken = authResponse.getToken();
    handleExistingToken(httpRequest, jwtToken);
    response.addCookie(generateCookie(jwtToken));
    log.info("User authenticated: {}", authResponse.getUserDto().getEmail());
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

  @GetMapping(value = "/user/validateTokenAndRefreshSession")
  public ResponseEntity<AuthenticaitonResponceDto> userRefresh(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    String token = jwtTokenUtil.extractTokenFromRequest(request);
    AuthenticaitonResponceDto authResponse = authenticationService.refreshSession(
      token
    );
    log.info("User refreshed: {}", authResponse.getUserDto().getEmail());
    return ResponseEntity.ok(authResponse);
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
    jwtCookie.setSecure(true);
    jwtCookie.setAttribute("SameSite", "None");
    if (jwtToken == null) {
      jwtCookie.setMaxAge(0);
      return jwtCookie;
    }
    int oneDayInSeconds = 60 * 60 * 24;
    jwtCookie.setMaxAge(oneDayInSeconds * jwtDayCountOfAuthValitiy);
    return jwtCookie;
  }

  /**
   * Handles an existing token by adding it to the blacklist.
   * Used on login and register to invalidate any existing tokens
   * also checks if the token is the same as the one in the request
   * for "re-login" events
   *
   * @param httpRequest
   */
  private void handleExistingToken(
    HttpServletRequest httpRequest,
    String jwtToken
  ) {
    String token = jwtTokenUtil.extractTokenFromRequest(httpRequest);
    if (token != null && !token.isEmpty() && !token.equals(jwtToken)) {
      jwtTokenUtil.addTokenToBlacklist(token);
    }
  }
}
