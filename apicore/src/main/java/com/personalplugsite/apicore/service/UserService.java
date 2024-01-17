package com.personalplugsite.apicore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personalplugsite.data.entities.User;
import com.personalplugsite.data.repos.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
