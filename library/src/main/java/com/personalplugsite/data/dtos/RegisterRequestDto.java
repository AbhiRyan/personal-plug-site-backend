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
public class RegisterRequestDto implements Serializable {

  private static final long serialVersionUID = -5933383689318339522L;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
