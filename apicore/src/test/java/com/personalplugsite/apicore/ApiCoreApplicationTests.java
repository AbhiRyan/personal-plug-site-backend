package com.personalplugsite.apicore;

import com.personalplugsite.data.repos.TokenBlacklistRepo;
import com.personalplugsite.data.repos.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
  properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:8100/personal-plug-db",
    "spring.datasource.username=${DB_USERNAME}",
    "spring.datasource.password=${DB_PASSWORD}",
    "spring.jpa.hibernate.ddl-auto=create-drop",
  }
)
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
