package com.personalplugsite.apicore.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.personalplugsite.data.entities.PersonLogin;
import com.personalplugsite.data.repos.PersonLoginRepo;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {
    PersonLoginRepo personLoginRepo;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers("/", "/home");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            PersonLogin personLogin = personLoginRepo.findByEmail(username);
            if (personLogin != null) {
                return User.builder()
                        .username(personLogin.getPersonEmail())
                        .password(personLogin.getPassword()) // assuming the password is already encoded
                        .roles("USER") // replace with actual roles if available
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }

    public boolean checkPassword(String submittedPassword, String email) {
        String encodedPassword = getStoredPassword(email);
        return passwordEncoder().matches(submittedPassword, encodedPassword);
    }

    public String getStoredPassword(String email) {
        return personLoginRepo.getPasswordFromEmail(email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(65536, 8, 1, 32, 16);
    }
}