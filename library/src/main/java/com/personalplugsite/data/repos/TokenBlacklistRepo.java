package com.personalplugsite.data.repos;

import com.personalplugsite.data.entities.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepo
  extends JpaRepository<BlacklistedToken, Integer> {}
