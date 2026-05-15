package com.examen.derechohabiente;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DerechohabienteApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DerechohabienteApplication.class, args);
		Arrays.asList(args).forEach(arg -> log.info(arg));
		System.exit(SpringApplication.exit(SpringApplication.run(DerechohabienteApplication.class, args)));
	}

}
