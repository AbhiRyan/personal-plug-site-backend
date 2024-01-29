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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
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

    // List of endpoints that should bypass the cookie check
    List<String> bypassEndpoints = Arrays.asList(
      "/api/auth/user/logout",
      "/api/auth/user/register",
      "/api/auth/user/authenticate"
    );

    // Get the current endpoint
    String requestEndpointPath = request.getRequestURI();

    if (!bypassEndpoints.contains(requestEndpointPath)) {
      boolean cookieIsValid = checkCookieValidity(request);

      if (!cookieIsValid) {
        Cookie cookie = new Cookie(jwtCookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        //return;
      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean checkCookieValidity(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return false;
    }

    for (Cookie cookie : cookies) {
      if (jwtCookieName.equals(cookie.getName()) && (cookie.getMaxAge() > 0)) {
        return true;
      }
    }
    return false;
  }
}
