package com.andall.sally.supply;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.andall.sally.supply.mapper")
@EnableAsync
@EnableScheduling
public class SupplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplyApplication.class, args);
    }

}
