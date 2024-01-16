package com.personalplugsite.apicore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.personalplugsite.apicore.config",
		"com.personalplugsite.apicore.service",
		"com.personalplugsite.apicore.api",
		"com.personalplugsite.data.dtos",
		"com.personalplugsite.data.enums",
		"com.personalplugsite.data.exception",
		"com.personalplugsite.data.entities",
		"com.personalplugsite.data.repos"
})
@EnableJpaRepositories(basePackages = "com.personalplugsite.data.repos")
@EntityScan("com.personalplugsite.data.entities")
public class ApiCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCoreApplication.class, args);
	}

}
