package com.adobe.asyncdemo.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AggregatorService {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Async("users-pool")
    public CompletableFuture<List<User>> getUsers() {
        System.out.println(Thread.currentThread() + " getting Users");
        return  CompletableFuture.completedFuture(userService.getUsers());
    }

    @Async("posts-pool")
    public CompletableFuture<List<Post>> getPosts() {
        System.out.println(Thread.currentThread() + " getting posts");
        return  CompletableFuture.completedFuture(postService.getPosts());
    }
}
