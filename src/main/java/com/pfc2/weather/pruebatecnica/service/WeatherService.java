package com.pfc2.weather.pruebatecnica.service;

import com.pfc2.weather.pruebatecnica.model.WeatherHistory;
import com.pfc2.weather.pruebatecnica.model.WeatherHistoryResponse;

import java.util.List;

public interface WeatherService {
    WeatherHistoryResponse getWeatherInformation(Double lat, Double lon);
    List<WeatherHistory> getAllDataInMemory();
}
