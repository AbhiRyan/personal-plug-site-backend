package com.personalplugsite.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.personalplugsite.data.entities.PersonLogin;

public interface PersonLoginRepo extends JpaRepository<PersonLogin, Long> {

    @Query("SELECT "
            + "password "
            + "FROM person_login "
            + "WHERE person_email = :email")
    public String getPasswordFromEmail(@Param("email") String email);

    @Query("SELECT "
            + "person_id "
            + ",person_email "
            + ",password "
            + "FROM person_login "
            + "WHERE person_email = :email")
    public PersonLogin findByEmail(@Param("email") String email);

}