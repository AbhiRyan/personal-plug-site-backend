package com.personalplugsite.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personalplugsite.data.entities.BlacklistedToken;

public interface TokenBlacklistRepo extends JpaRepository<BlacklistedToken, Integer> {

}
