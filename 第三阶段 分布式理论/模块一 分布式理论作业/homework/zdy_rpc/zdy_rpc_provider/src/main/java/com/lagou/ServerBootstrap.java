package com.lagou;

import com.lagou.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerBootstrap {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ServerBootstrap.class, args);
        UserServiceImpl.startServer("127.0.0.1", 8990);
    }
}
