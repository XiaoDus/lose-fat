package com.lf.backstage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.lf.backstage.mapper")
public class LoseFatBackstageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoseFatBackstageApplication.class, args);
    }

}
