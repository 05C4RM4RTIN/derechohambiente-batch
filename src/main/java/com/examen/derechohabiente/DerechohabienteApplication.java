package com.examen.derechohabiente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DerechohabienteApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DerechohabienteApplication.class, args);
		
		System.exit(SpringApplication.exit(SpringApplication.run(DerechohabienteApplication.class, args)));
	}

}
