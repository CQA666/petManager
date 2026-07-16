package com.pet.petmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pet.petmanager.dao")//扫描dao包
public class PetManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetManagerApplication.class, args);
    }

}
