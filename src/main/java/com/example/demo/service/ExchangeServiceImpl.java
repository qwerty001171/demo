package com.example.demo.service;

import com.example.demo.dto.ExchangeRubResponseDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;


@Service
public class ExchangeServiceImpl implements ExchangeService {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String APP_ID = "c668e353f5da4866b0466ce87b1ddb75";
    private static final String SYMBOL = "RUB";
    private static final String GIF_API_KEY = "WP1JkVs067RY1fTgMYPY1Bs0zmo7qvPb";

    private final ExchangeClient exchangeClient;
    private final GifClient gifClient;

    public ExchangeServiceImpl(ExchangeClient exchangeClient, GifClient gifClient) {
        this.exchangeClient = exchangeClient;
        this.gifClient = gifClient;
    }

    @Override
    public String get(String base) {
        try {
            CompletableFuture<ExchangeRubResponseDto> yesterday = getYesterday(base);
            CompletableFuture<ExchangeRubResponseDto> now = getNow(base);

            CompletableFuture.allOf(yesterday, now).join();

            ExchangeRubResponseDto y = yesterday.get();
            ExchangeRubResponseDto n = now.get();

            if (y.getRatesRUB() < n.getRatesRUB()) {
                return gifClient.get(GIF_API_KEY, "rich", "g");
            } else {
                return gifClient.get(GIF_API_KEY, "broke", "g");
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return "Error when get";
        }
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<ExchangeRubResponseDto> getYesterday(String base) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return CompletableFuture.completedFuture(exchangeClient.get(
                yesterday.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                APP_ID,
                base,
                SYMBOL
        ));
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<ExchangeRubResponseDto> getNow(String base) {
        return CompletableFuture.completedFuture(exchangeClient.get(
                LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                APP_ID,
                base,
                SYMBOL
        ));
    }
}
