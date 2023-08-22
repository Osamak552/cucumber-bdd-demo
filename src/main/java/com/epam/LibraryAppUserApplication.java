package com.epam;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LibraryAppUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAppUserApplication.class, args);

	}

	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

}
