package com.personalplugsite.data.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person_login")
@Getter
@Setter
public class PersonLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(name = "person_email")
    private String personEmail;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "personLogin", cascade = CascadeType.ALL)
    private PersonDetails personDetails;
}
