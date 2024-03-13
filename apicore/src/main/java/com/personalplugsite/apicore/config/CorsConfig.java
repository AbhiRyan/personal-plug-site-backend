package com.personalplugsite.apicore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Value("${localised-values.CORS_ALLOWED_ORIGINS}")
  private String corsAllowedOrigins;

  @Override
  public void addCorsMappings(@NonNull CorsRegistry registry) {
    registry
      .addMapping("/**")
      .allowedOrigins(corsAllowedOrigins)
      .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
      .allowCredentials(true);
  }
}
