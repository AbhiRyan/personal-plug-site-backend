package com.personalplugsite.apicore.service;

import org.springframework.stereotype.Service;

import com.personalplugsite.data.dtos.PersonDto;
import com.personalplugsite.data.entities.Person;
import com.personalplugsite.data.repos.PersonRepo;

@Service

public class PersonService {
    private PersonRepo personRepo;

    public PersonDto getPerson(String nameSearch) {
        if (nameSearch.isEmpty()) {
            throw new IllegalArgumentException("nameSearch cannot be empty");
        }
        Person person = personRepo.getPersonFromDb(nameSearch);
        return new PersonDto(person.getPersonId(), person.getPersonName(), person.getPersonEmail());
    }
}