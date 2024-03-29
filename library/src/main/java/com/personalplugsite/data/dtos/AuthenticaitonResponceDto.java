package com.personalplugsite.data.dtos;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticaitonResponceDto implements Serializable {

  private static final long serialVersionUID = -5830921380190649119L;
  private String token;
  private UserDto userDto;
}
