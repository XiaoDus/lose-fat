package com.lf.losefat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@MapperScan("com.lf.losefat.mapper")
@EnableScheduling //定时器
public class LoseFatApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoseFatApplication.class, args);
	}

}
