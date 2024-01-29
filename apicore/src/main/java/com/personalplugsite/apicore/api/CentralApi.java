package com.personalplugsite.apicore.api;

import com.personalplugsite.data.dtos.MessageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/central")
@RequiredArgsConstructor
@Slf4j
public class CentralApi {

  @GetMapping(value = "/test-string")
  public ResponseEntity<MessageDto> testString() {
    log.info("validated test-string respoce sent");
    MessageDto messageDto = MessageDto.builder().message("Test String").build();
    return ResponseEntity.ok(messageDto);
  }
}
