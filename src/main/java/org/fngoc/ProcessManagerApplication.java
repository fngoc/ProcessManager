package org.fngoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProcessManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessManagerApplication.class, args);
    }

}
