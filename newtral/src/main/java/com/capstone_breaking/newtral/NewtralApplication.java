package com.capstone_breaking.newtral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NewtralApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewtralApplication.class, args);
	}

}
