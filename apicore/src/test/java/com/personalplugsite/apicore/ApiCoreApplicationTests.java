package com.personalplugsite.apicore;

import com.personalplugsite.data.repos.TokenBlacklistRepo;
import com.personalplugsite.data.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ApiCoreApplicationTests {

  @MockBean
  private TokenBlacklistRepo tokenBlacklistRepo;

  @MockBean
  private UserRepo userRepo;

  @Test
  void contextLoads() {
    // This test will pass if the application context loads successfully
  }
}
