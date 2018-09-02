package com.realfaceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class RealFaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealFaceApplication.class, args);
	}	
}
