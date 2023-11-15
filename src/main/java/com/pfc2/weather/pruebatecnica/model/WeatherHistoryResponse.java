package com.pfc2.weather.pruebatecnica.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherHistoryResponse {
    private String weather;
    private Double tempMin;
    private Double tempMax;
    private Integer humidity;
}
