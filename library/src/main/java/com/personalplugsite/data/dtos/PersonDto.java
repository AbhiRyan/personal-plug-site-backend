package com.personalplugsite.data.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonDto implements Serializable {

    private static final long serialVersionUID = -6195885788556845292L;
    private Long personId;
    private String personName;
    private String personEmail;
}
