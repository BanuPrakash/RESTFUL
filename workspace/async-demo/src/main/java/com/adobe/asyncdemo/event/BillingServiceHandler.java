package com.adobe.asyncdemo.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BillingServiceHandler {

    @EventListener
    @Async
    public void processBill(PatientDischargeEvent event) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread() + " : " + " billing service " + event.getName());
    }
}
