package com.personalplugsite.apicore.config;

import com.personalplugsite.data.entities.BlacklistedToken;
import com.personalplugsite.data.entities.User;
import com.personalplugsite.data.repos.TokenBlacklistRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class JwtTokenUtil {

  private final TokenBlacklistRepo tokenBlacklistRepo;

  @Value("${localised-values.SECRET_KEY}")
  private String secretKey;

  @Value("${pps-app.jwt.cookieName}")
  private String jwtCookieName;

  @Value("${pps-app.jwt.dayCountOfAuthValitiy}")
  private int jwtDayCountOfAuthValitiy;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @NonNull
  public Integer extractUserId(String token) {
    var userId = extractClaim(token, claims -> claims.get("id"));
    return Integer.parseInt(String.valueOf(userId));
  }

  private Date extrctExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(User user) {
    return generateToken(new HashMap<>(), user);
  }

  public String generateToken(Map<String, Object> extraClaims, User user) {
    return Jwts
      .builder()
      .claims(extraClaims)
      .claim("id", user.getId())
      .subject(user.getUsername())
      .issuedAt(Date.from(Instant.now()))
      .expiration(
        Date.from(Instant.now().plus(jwtDayCountOfAuthValitiy, ChronoUnit.DAYS))
      )
      .signWith(getSignInKey())
      .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extrctExpiration(token).before(Date.from(Instant.now()));
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parser()
      .verifyWith(getSignInKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public void addTokenToBlacklist(String token) {
    BlacklistedToken tokenBlacklist = new BlacklistedToken();
    tokenBlacklist.setId(extractUserId(token));
    tokenBlacklist.setToken(token);
    tokenBlacklistRepo.save(tokenBlacklist);
  }

  public void removeUserTokensFromBlacklist(@NonNull Integer userId) {
    tokenBlacklistRepo.deleteById(userId);
  }

  public String extractTokenFromRequest(HttpServletRequest request) {
    String jwt = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (jwtCookieName.equals(cookie.getName())) {
          jwt = cookie.getValue();
          break;
        }
      }
    }
    return jwt;
  }

  /**
   * Checks if the token is blacklisted and throws an exception if it is
   *
   * @param token
   */
  public void blacklistedTokenCheck(String token) {
    Integer userId = extractUserId(token);
    tokenBlacklistRepo
      .findById(userId)
      .ifPresent(tokenBlacklist -> {
        if (tokenBlacklist.getToken().equals(token)) {
          throw new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Unauthorized"
          );
        }
      });
  }
}
