package com.ericjeffrey.HelloSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringBootApplication {
	public static String content = "int main() { return 0; }";

    public static void main(String[] args) {
		SpringApplication.run(HelloSpringBootApplication.class, args);
	}
}
