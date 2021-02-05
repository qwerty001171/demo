package com.example.demo.controller;

import com.example.demo.service.ExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    /**
     * @param base currency example(USD)
     * @return String json gif
     */
    @GetMapping(value = "/{base}")
    public String get(@PathVariable("base") String base) {
        return exchangeService.get(base.toUpperCase());
    }
}
