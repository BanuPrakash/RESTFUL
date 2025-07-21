package com.adobe.demo.pubsub;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class EventBusSubscriberBeanPostProcessor implements BeanPostProcessor {

    private final EventBus eventBus;

//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
//    }

    // constructor DI, no need for @Autowired

    @Lazy
    public EventBusSubscriberBeanPostProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        Annotation annotation = bean.getClass().getAnnotation(Subscribe.class);
//        if(annotation == null) {
//            System.out.println("Register " + beanName +" with EventBus");
//            eventBus.register((MyEventSubscriber) bean);
//        }
        if(bean instanceof MyEventSubscriber) {
            System.out.println("Register " + beanName +" with EventBus");
            eventBus.register((MyEventSubscriber) bean);
        }
        return bean;
    }
}
