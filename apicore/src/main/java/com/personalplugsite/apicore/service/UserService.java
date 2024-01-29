package com.personalplugsite.apicore.service;

import com.personalplugsite.data.entities.User;
import com.personalplugsite.data.repos.UserRepo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepo userRepo;

  public Optional<User> findByEmail(String email) {
    return userRepo.findByEmail(email);
  }
}
