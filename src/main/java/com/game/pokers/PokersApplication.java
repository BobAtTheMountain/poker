package com.game.pokers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class}
)
public class PokersApplication {

    public static void main(String[] args) {
        System.out.println("aaa");
        SpringApplication.run(PokersApplication.class, args);
    }

}
