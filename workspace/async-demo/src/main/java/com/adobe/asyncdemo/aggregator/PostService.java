package com.adobe.asyncdemo.aggregator;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url="/posts", accept = "application/json", contentType = "application/json")
public interface PostService {
    @GetExchange
    List<Post> getPosts();

    @GetExchange("/{id}")
    Post getPost(@PathVariable("id") int id);
}
