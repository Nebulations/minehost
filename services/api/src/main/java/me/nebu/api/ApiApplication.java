package me.nebu.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		Database.start();
		SpringApplication.run(ApiApplication.class, args);
	}

}
