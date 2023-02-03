package org.example;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class TransmitterApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(TransmitterApplication.class, args);
        ctx.close();
    }


}
