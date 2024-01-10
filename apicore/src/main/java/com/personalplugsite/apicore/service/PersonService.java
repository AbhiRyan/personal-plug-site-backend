package com.personalplugsite.apicore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.personalplugsite.data.dtos.PersonDto;
import com.personalplugsite.data.entities.Person;
import com.personalplugsite.data.entities.PersonDetails;
import com.personalplugsite.data.entities.PersonLogin;
import com.personalplugsite.data.repos.PersonDetailsRepo;
import com.personalplugsite.data.repos.PersonLoginRepo;
import com.personalplugsite.data.repos.PersonRepo;

import jakarta.transaction.Transactional;

@Service

public class PersonService {
    private PersonRepo personRepo;
    private PersonLoginRepo personLoginRepo;
    private PersonDetailsRepo personDetailsRepo;

    public PersonDto getPersonFromEmail(String email) {
        if (email.isEmpty()) {
            throw new IllegalArgumentException("email cannot be empty");
        }
        Person person = personRepo.getPersonByEmail(email);
        return new PersonDto(person.getPersonId(), person.getPersonName(), person.getPersonEmail(),
                person.getPersonLastName(), person.getPersonOtherName());
    }

    public PersonDto getPersonFromId(Long personId) {
        if (personId == null) {
            throw new IllegalArgumentException("personId cannot be empty");
        }
        Person person = personRepo.getPersonById(personId);
        return new PersonDto(person.getPersonId(), person.getPersonName(), person.getPersonEmail(),
                person.getPersonLastName(), person.getPersonOtherName());
    }

    public List<PersonDto> getPersonDtoListFromName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        return personRepo.getPersonListfromName(name).stream()
                .map(person -> new PersonDto(person.getPersonId(), person.getPersonName(), person.getPersonEmail(),
                        person.getPersonLastName(), person.getPersonOtherName()))
                .collect(Collectors.toList());
    }

    public String getPassword(String email) {
        return personLoginRepo.getPasswordFromEmail(email);
    }

    @Transactional
    public void createPerson(String email, String password, String name, String lastName, String otherName) {
        PersonLogin personLogin = new PersonLogin();
        personLogin.setPersonEmail(email);
        personLogin.setPassword(password);
        personLoginRepo.save(personLogin);

        PersonDetails personDetails = new PersonDetails();
        personDetails.setPersonName(name);
        personDetails.setPersonLastName(lastName);
        personDetails.setPersonOtherName(otherName);
        personDetails.setPersonLogin(personLogin);
        personDetailsRepo.save(personDetails);
    }

}