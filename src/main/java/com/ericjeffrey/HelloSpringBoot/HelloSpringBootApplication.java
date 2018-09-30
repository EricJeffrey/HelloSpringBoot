package com.ericjeffrey.HelloSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringBootApplication {
    private static final boolean DEBUG = false;

    public static final String WORD_DIR_PATH = HelloSpringBootApplication.DEBUG ? "E:\\temp\\" : "/home/ubuntu/EricJeffreyPastor/";

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBootApplication.class, args);
    }
}
