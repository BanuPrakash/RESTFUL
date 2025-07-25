package com.adobe.asyncdemo.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BillingServiceHandler {

    @EventListener
    @Async
    public void processBill(PatientDischargeEvent event) {
        System.out.println(Thread.currentThread() + " : " + " billing service " + event.getName());
    }
}
