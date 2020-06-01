package com.diff.filesdiff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FilesDiffApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesDiffApplication.class, args);
    }
}
