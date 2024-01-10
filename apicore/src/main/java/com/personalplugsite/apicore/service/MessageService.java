package com.personalplugsite.apicore.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(MessageServiceProperties.class)
public class MessageService {

    private MessageServiceProperties properties;

    public MessageService(MessageServiceProperties properties) {
        this.properties = properties;
    }

    public String getMessage() {
        return properties.getMessage();
    }

    public void setMessage(String message) {
        properties.setMessage(message);
    }
}
