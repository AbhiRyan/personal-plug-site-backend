package com.personalplugsite.apicore.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personalplugsite.apicore.service.PersonService;
import com.personalplugsite.data.dtos.PersonDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/person")
@RequiredArgsConstructor
public class PersonApi {
    PersonService personService;

    // @ApiOperation(value = "GetPerson", notes = "get person data")
    @PostMapping(value = "/getPerson")
    public PersonDto gerPersonEndPoint(@RequestBody String nameSearch) {
        return personService.getPerson(nameSearch);
    }
}
