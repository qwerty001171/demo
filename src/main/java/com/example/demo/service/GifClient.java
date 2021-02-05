package com.example.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// https://api.giphy.com/v1/gifs/random?api_key=WP1JkVs067RY1fTgMYPY1Bs0zmo7qvPb&tag=rich&rating=g
@FeignClient(name = "rich-gif-client", url = "https://api.giphy.com/v1/gifs/random")
public interface GifClient {
    @GetMapping
    String get(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag,
            @RequestParam("rating") String rating
    );
}
