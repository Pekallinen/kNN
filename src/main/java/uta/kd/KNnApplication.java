package uta.kd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import uta.kd.service.KNNService;

@SpringBootApplication
public class KNnApplication {
    
    @Autowired
    private KNNService knnService;

    public static void main(String[] args) {
        SpringApplication.run(KNnApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
           knnService.doKNN(args);
        };
    }
}
