package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangeRubResponseDto {
    private String base;
    private Rate rates;

    public double getRatesRUB() {
        return rates.RUB;
    }

    @Data
    static class Rate {
        @JsonProperty(value = "RUB")
        public double RUB;
    }
}
