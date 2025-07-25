package com.adobe.asyncdemo.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/posts")
public class PostController {
    @Autowired
    AggregatorService service;

    @GetMapping()
    public List<PostsDTO> getPosts() {
        CompletableFuture<List<User>> users = service.getUsers(); // non blocking
        CompletableFuture<List<Post>> posts = service.getPosts(); // non blocking
        // barrier blocking code
        List<Post> postList = posts.join();
        List<User> userList = users.join();

        return postList.stream().map(post -> {
            String username = userList.stream().filter(user -> user.id() == post.userId())
                    .findFirst().get().name();
            return  new PostsDTO(post.title(), username);
        }).collect(Collectors.toList());
    }
}
