package com.adobe.demo.pubsub;

import org.springframework.stereotype.Service;

@Service
public class SomeService implements MyEventSubscriber {
    @Override
    public void onEvent(CustomEvent event) {
        System.out.println("SomeService Got : " + event);
    }
}
