package com.example.springlabb;

import com.example.springlabb.dtos.MovieDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SpringBootApplication
public class SpringlabbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringlabbApplication.class, args);
    }


}

