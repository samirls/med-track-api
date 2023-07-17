package com.api.medtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MedTrackApplication {

	public static void main(String[] args) {

		SpringApplication.run(MedTrackApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

	@GetMapping("/")
	public String index(){
		return "Med Track";
	}
}
