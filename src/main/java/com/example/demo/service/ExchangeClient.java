package com.example.demo.service;

import com.example.demo.dto.ExchangeRubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchange-client", url = "https://openexchangerates.org/api/historical")
public interface ExchangeClient {

    @GetMapping(value = "/{date}.json")
    ExchangeRubResponseDto get(
            @PathVariable("date") String date,
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base,
            @RequestParam("symbols") String symbol
    );
}
