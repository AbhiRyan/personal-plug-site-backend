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
public class UserDto implements Serializable {

  private static final long serialVersionUID = -5247873174031499937L;
  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
}
