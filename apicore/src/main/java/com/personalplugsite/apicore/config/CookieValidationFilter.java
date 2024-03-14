package com.personalplugsite.apicore.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CookieValidationFilter implements Filter {

  @Value("${pps-app.jwt.cookieName}")
  private String jwtCookieName;

  @Override
  public void doFilter(
    ServletRequest servletRequest,
    ServletResponse servletResponse,
    FilterChain filterChain
  ) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    log.info(
      "CookieValidationFilter hit: " +
      request.getRequestURI() +
      "-------------------"
    );

    // List of endpoints that should bypass the cookie check
    List<String> bypassEndpoints = Arrays.asList(
      "/api/auth/user/logout",
      "/api/auth/user/register",
      "/api/auth/user/authenticate"
    );

    // Get the current endpoint
    String requestEndpointPath = request.getRequestURI();

    if (!bypassEndpoints.contains(requestEndpointPath)) {
      log.info(
        requestEndpointPath + " is not in the bypass list -------------------"
      );

      boolean cookieIsValid = checkCookieValidity(request);

      if (!cookieIsValid) {
        log.info("Cookie is not valid -------------------");
        Cookie cookie = new Cookie("cookieName", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/auth");
        return;
      }
    } else {
      log.info(
        requestEndpointPath + " is in the bypass list -------------------"
      );
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean checkCookieValidity(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      log.info("No cookies found -------------------");
      return false;
    }

    for (Cookie cookie : cookies) {
      if (jwtCookieName.equals(cookie.getName())) {
        log.info(jwtCookieName + " cookie found -------------------");
        return true;
      }
    }
    log.info(jwtCookieName + " cookie not found -------------------");
    return false;
  }
}
