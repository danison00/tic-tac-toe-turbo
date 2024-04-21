package com.dandev.tictactoeturbo;

import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.repository.UserRespository;
import com.dandev.tictactoeturbo.util.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private UserRespository userRespository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }


    @Override
    public void run(String... args) throws Exception {
//        userRespository.save(new User(UUID.fromString("84a491f0-5369-4c53-845f-6afc0aa23c49"), "Dânison Gomes dos Santos", "danison@email"));
//        userRespository.save(new User(UUID.fromString("05190e92-b442-4aab-9d20-c7c98a77c09f"), "João Vitor Cabral Lima", "joao@email"));

        System.out.println(new BCryptPasswordEncoder().encode("12345"));
        new BCryptPasswordEncoder().matches("oioidanison", "$2a$10$QZ50M2qdj/0NWvuD6/db..YmdsfnNVmP77LXizKYatzGCNoOQjQfa");
        System.out.println(UUID.randomUUID());

    }
}
