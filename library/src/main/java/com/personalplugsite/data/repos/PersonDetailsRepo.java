package com.personalplugsite.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.personalplugsite.data.entities.PersonDetails;

public interface PersonDetailsRepo extends JpaRepository<PersonDetails, Long> {

    @Query("SELECT "
            + "person_login.person_id "
            + ",person_name "
            + ",person_lastname "
            + ",person_othername "
            + ",person_email "
            + "FROM "
            + "person_login "
            + "LEFT JOIN person_details ON person_login.person_id = person_details.person_id"
            + "WHERE person_name contains :email")
    public PersonDetails getPersonListfromName(@Param("personName") String personName);
}
