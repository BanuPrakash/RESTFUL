package com.adobe.demo;

import com.adobe.demo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        // get from container
        AppService appService = context.getBean("appService", AppService.class);
        appService.doTask();
    }

}
