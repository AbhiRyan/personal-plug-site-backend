package com.personalplugsite.apicore.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/central")
public class CentralApi {

    @GetMapping(value = "/test-string")
    public ResponseEntity<String> testString() {
        return ResponseEntity.ok("Test String");
    }

}
