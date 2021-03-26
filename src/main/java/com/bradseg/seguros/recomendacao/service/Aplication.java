package com.bradseg.seguros.recomendacao.service;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Aplication {

   public static void main(String[] args) {
	   SpringApplication app = new SpringApplication(Aplication.class);
       app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
       app.run(args);
	 

   }
}