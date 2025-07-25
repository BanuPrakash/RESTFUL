package com.adobe.asyncdemo.aggregator;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
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

    private  final Counter counter;
    private Timer timer;

    public PostController(MeterRegistry registry) {
        counter = Counter.builder("posts.counter")
                .description("Counts Posts API call")
                .tag("region", "us-east")
                .register(registry);

        timer = Timer.builder("posts.timer")
                .description("Times Posts API")
                .register(registry);
    }

    @GetMapping()
    public List<PostsDTO> getPosts() throws  Exception {
        counter.increment();


        return timer.recordCallable(() -> this.getPostsDTO());
    }

    private List<PostsDTO> getPostsDTO() {
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
