package com.personalplugsite.apicore.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personalplugsite.apicore.service.MessageService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/api/message")
public class MessageApi {
    private MessageService messageService;

    @GetMapping(value = "/getMessage")
    public String getMessage() {
        return messageService.getMessage();
    }

    @GetMapping(value = "/setMessage/{message}")
    public void setMessage(@PathParam("message") String message) {
        messageService.setMessage(message);
    }

}
