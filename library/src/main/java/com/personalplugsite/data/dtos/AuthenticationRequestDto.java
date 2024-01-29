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
public class AuthenticationRequestDto implements Serializable {

  private static final long serialVersionUID = 7228044943259648451L;
  private String email;
  private String password;
}
