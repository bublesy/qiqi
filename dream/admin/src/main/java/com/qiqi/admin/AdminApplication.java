package com.qiqi.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author QiQiDream
 */
@SpringBootApplication
@MapperScan("com.qiqi.**.mapper")
@ComponentScan(basePackages = {"com.qiqi.*"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
