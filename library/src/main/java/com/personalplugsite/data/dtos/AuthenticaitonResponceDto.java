package com.personalplugsite.data.dtos;

import com.personalplugsite.data.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticaitonResponceDto {

  private String token;
  private User user;
}
