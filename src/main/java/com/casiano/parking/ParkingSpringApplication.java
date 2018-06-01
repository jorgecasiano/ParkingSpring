package com.casiano.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class ParkingSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingSpringApplication.class, args);
	}
	
}
