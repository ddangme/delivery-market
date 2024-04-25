package com.ddangme.dmadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DMAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DMAdminApplication.class, args);
    }

}
