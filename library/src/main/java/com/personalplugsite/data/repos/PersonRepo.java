package com.personalplugsite.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.personalplugsite.data.entities.Person;

public interface PersonRepo extends JpaRepository<Person, Long> {

    @Query("SELECT "
            + "person_login.person_id "
            + ",person_name "
            + ",person_lastname "
            + ",person_othername "
            + ",person_email "
            + "FROM "
            + "person_login "
            + "LEFT JOIN person_details ON person_login.person_id = person_details.person_id"
            + "WHERE person_email = :email")
    public Person getPersonByEmail(@Param("email") String email);

    @Query("SELECT "
            + "person_login.person_id "
            + ",person_name "
            + ",person_lastname "
            + ",person_othername "
            + ",person_email "
            + "FROM "
            + "person_login "
            + "LEFT JOIN person_details ON person_login.person_id = person_details.person_id"
            + "WHERE person_id = :personId")
    public Person getPersonById(@Param("personId") Long personId);

    @Query("SELECT "
            + "person_login.person_id "
            + ",person_name "
            + ",person_lastname "
            + ",person_othername "
            + ",person_email "
            + "FROM "
            + "person_login "
            + "LEFT JOIN person_details ON person_login.person_id = person_details.person_id"
            + "WHERE person_name contains :personName")
    public List<Person> getPersonListfromName(@Param("personName") String personName);

}
