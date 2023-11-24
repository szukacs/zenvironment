package com.useclient.zenvironment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ZenvironmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZenvironmentApplication.class, args);
    }

}
