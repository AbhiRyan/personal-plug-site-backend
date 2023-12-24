package com.personalplugsite.apicore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = { "com.personalplugsite.data" })
@ComponentScan(basePackages = { "com.personalplugsite.apicore.config", "com.personalplugsite.apicore.service",
		"com.personalplugsite.apicore.api",
		/* "com.personalplugsite.data.repos", */ "com.personalplugsite.data.entities"
})
public class ApiCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCoreApplication.class, args);
	}

}
