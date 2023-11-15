package com.pfc2.weather.pruebatecnica.service.impl;

import com.pfc2.weather.pruebatecnica.adapter.WeatherServiceAdapter;
import com.pfc2.weather.pruebatecnica.model.WeatherHistory;
import com.pfc2.weather.pruebatecnica.model.WeatherHistoryResponse;
import com.pfc2.weather.pruebatecnica.repository.WeatherHistoryRepository;
import com.pfc2.weather.pruebatecnica.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherHistoryRepository weatherHistoryRepository;
    private final WeatherServiceAdapter weatherServiceAdapter;
    @Override
    public WeatherHistoryResponse getWeatherInformation(Double lat, Double lon) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenMinutesAgo = now.minusMinutes(10);
        Date date = Date.from(tenMinutesAgo.atZone(java.time.ZoneId.systemDefault()).toInstant());

        var weatherReviewed = weatherHistoryRepository.findByLatAndLon(lat, lon, date).orElse(null);
        if(null != weatherReviewed){
            log.info("You have information saved in the DB with this cords: {}", weatherReviewed);
            return WeatherHistoryResponse.builder()
                    .weather(weatherReviewed.getWeather())
                    .tempMin(weatherReviewed.getTempMin())
                    .tempMax(weatherReviewed.getTempMax())
                    .humidity(weatherReviewed.getHumidity())
                    .build();

        }else {
            log.info("Before Call the weather api");
            var weatherFromApi = weatherServiceAdapter.getWeatherDataFromApi(lat, lon);
            log.info("After Call the weather api: {}", weatherFromApi);
            if(null != weatherFromApi){
                var weatherToSave = WeatherHistory.builder()
                        .weather(weatherFromApi.getWeather().get(0).getMain())
                        .created(Date.from(Instant.now()))
                        .tempMax(weatherFromApi.getMain().getTemp_max())
                        .tempMin(weatherFromApi.getMain().getTemp_min())
                        .humidity(weatherFromApi.getMain().getHumidity())
                        .lat(lat)
                        .lon(lon)
                        .build();
                weatherHistoryRepository.save(weatherToSave);
                return WeatherHistoryResponse.builder()
                        .weather(weatherToSave.getWeather())
                        .tempMin(weatherToSave.getTempMin())
                        .tempMax(weatherToSave.getTempMax())
                        .humidity(weatherToSave.getHumidity())
                        .build();

            }else {
                throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred in the api to call the weather endpoint " );
            }

        }


    }

    @Override
    public List<WeatherHistory> getAllDataInMemory() {
        try{
            return weatherHistoryRepository.findAll();
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred in the api to call the weather endpoint "+e.getMessage() );
        }

    }
}
