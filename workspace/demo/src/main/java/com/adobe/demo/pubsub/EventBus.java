package com.adobe.demo.pubsub;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventBus {
    List<MyEventSubscriber> subscribers = new ArrayList<>();

    public void register(MyEventSubscriber subscriber) {
        subscribers.add(subscriber);
        System.out.println("Registered : " + subscriber.getClass().getSimpleName());
    }

    public void postMessage(CustomEvent event) {
        for(MyEventSubscriber subscriber: subscribers) {
            subscriber.onEvent(event);
        }
    }
}
