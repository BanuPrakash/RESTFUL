package com.adobe.asyncdemo.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class HouseKeepingHandler {

    @EventListener
    @Async
    public void processHouseKeeping(PatientDischargeEvent event) {
        System.out.println(Thread.currentThread() + " : " + " house keeping service " + event.getName());
    }
}
