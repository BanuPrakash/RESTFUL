package com.adobe.asyncdemo.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class HouseKeepingHandler {

    @EventListener
    @Async
    public void processHouseKeeping(PatientDischargeEvent event) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread() + " : " + " house keeping service " + event.getName());
        // can return result to Kafka / Kinesis stream /Redis
    }
}
