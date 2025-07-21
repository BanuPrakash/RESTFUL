package com.adobe.demo.pubsub;

import org.springframework.stereotype.Service;

@Service
public class MyService implements MyEventSubscriber {
    @Override
    public void onEvent(CustomEvent event) {
        System.out.println("MyService Got : " + event);
    }
}
//
//@Service
//@Subscribe
//public class MyService implements MyEventSubscriber {
//    @Override
//    public void onEvent(CustomEvent event) {
//        System.out.println("MyService Got : " + event);
//    }
//}