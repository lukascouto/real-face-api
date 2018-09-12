package com.realfaceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages= {"com.*"})
public class RealFaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealFaceApplication.class, args);
	}	
}
