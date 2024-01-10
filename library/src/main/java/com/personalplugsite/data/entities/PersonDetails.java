package com.personalplugsite.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person_details")
@Getter
@Setter
public class PersonDetails {
    @Id
    private Long personId;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "person_lastname")
    private String personLastName;

    @Column(name = "person_othername")
    private String personOtherName;

    @OneToOne
    @JoinColumn(name = "person_id")
    @MapsId
    private PersonLogin personLogin;

}
