package com.adobe.demo;

import com.adobe.demo.pubsub.CustomEvent;
import com.adobe.demo.pubsub.EventBus;
import com.adobe.demo.service.AppService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        String[] names = context.getBeanDefinitionNames();
        for(String name : names) {
            System.out.println(name);
        }
        // get from container
        AppService appService = context.getBean("appService", AppService.class);
        appService.doTask();

        EventBus eventBus = (EventBus) context.getBean("eventBus", EventBus.class);
        eventBus.postMessage(new CustomEvent("First Message", new Date()));
        eventBus.postMessage(new CustomEvent("Second Message", new Date()));

        System.out.println("*****");

        SampleConfig config = (SampleConfig) context.getBean("sampleConfig", SampleConfig.class);
        System.out.println(config.getUrl()  + " : " + config.getDriver());

    }

}
