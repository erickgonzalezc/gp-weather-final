package com.pfc2.weather.pruebatecnica.service;

import com.pfc2.weather.pruebatecnica.model.WeatherHistory;
import com.pfc2.weather.pruebatecnica.repository.WeatherHistoryRepository;
import com.pfc2.weather.pruebatecnica.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    @Mock
    private WeatherHistoryRepository weatherHistoryRepository;
    @InjectMocks
    private WeatherServiceImpl weatherService;

    public static WeatherHistory createSampleWeatherHistory(long id, double lat, double lon, String weather,
                                                            double tempMin, double tempMax, int humidity) {
        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setId(id);
        weatherHistory.setLat(lat);
        weatherHistory.setLon(lon);
        weatherHistory.setWeather(weather);
        weatherHistory.setTempMin(tempMin);
        weatherHistory.setTempMax(tempMax);
        weatherHistory.setHumidity(humidity);
        weatherHistory.setCreated(new Date());

        return weatherHistory;
    }
    @Test
     void testGetAllDataInMemory() {
        // Arrange
        WeatherHistory weatherHistory1 = createSampleWeatherHistory(1L, 40.7128, -74.0060, "Sunny", 15.0, 25.0, 50);
        WeatherHistory weatherHistory2 = createSampleWeatherHistory(2L, 34.0522, -118.2437, "Cloudy", 12.0, 20.0, 65);

        List<WeatherHistory> expectedWeatherHistoryList = Arrays.asList(weatherHistory1, weatherHistory2);

        // Mock the behavior of the repository
        Mockito.when(weatherHistoryRepository.findAll()).thenReturn(expectedWeatherHistoryList);

        // Act
        List<WeatherHistory> actualWeatherHistoryList = weatherService.getAllDataInMemory();

        assertEquals(expectedWeatherHistoryList, actualWeatherHistoryList);
    }

}