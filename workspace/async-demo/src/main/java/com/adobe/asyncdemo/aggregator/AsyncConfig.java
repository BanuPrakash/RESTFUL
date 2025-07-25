package com.adobe.asyncdemo.aggregator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
// @EnableAsync is present in main class
public class AsyncConfig {

    @Bean
    PostService postService() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return  factory.createClient(PostService.class);
    }


    @Bean
    UserService userService() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return  factory.createClient(UserService.class);
    }

    @Bean(name="posts-pool")
    public Executor postsPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("POSTS-POOL");
        executor.initialize();
        return  executor;
//        ExecutorService service = Executors.newFixedThreadPool(10)
    }

    @Bean(name="users-pool")
    public Executor usersPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("USERS-POOL");
        executor.initialize();
        return  executor;
//        ExecutorService service = Executors.newFixedThreadPool(10)
    }
}
