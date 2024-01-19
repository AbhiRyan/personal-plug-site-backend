package com.personalplugsite.data.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.personalplugsite.data.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
