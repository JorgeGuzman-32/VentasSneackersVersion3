package com.example.venta_sneackers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Collections;

@SpringBootApplication
public class VentaSneackersApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(VentaSneackersApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "9090"));
		app.run(args);
	}

}
