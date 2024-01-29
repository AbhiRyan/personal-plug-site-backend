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
public class MessageDto implements Serializable {

  private static final long serialVersionUID = -6446710407875843972L;
  private String message;
}
