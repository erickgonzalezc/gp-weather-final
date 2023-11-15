package com.pfc2.weather.pruebatecnica.controller;

import com.pfc2.weather.pruebatecnica.model.CordPayload;
import com.pfc2.weather.pruebatecnica.model.WeatherHistory;
import com.pfc2.weather.pruebatecnica.model.WeatherHistoryResponse;
import com.pfc2.weather.pruebatecnica.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class WeatherController {
    private final WeatherService weatherService;
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v1/weather")
    public WeatherHistoryResponse getWeatherInfoByCord(@RequestBody CordPayload payload){

        log.debug("Inside the weather endpoint with the following parameters: {}", payload);
        if (null == payload) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Payload Please make sure you have a correct data " );
        }
        return weatherService.getWeatherInformation(payload.getLat(), payload.getLon());

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/weather/history")
    public List<WeatherHistory> getAllHistoryWeatherData(){
        log.debug("Inside the history weather ");
        return weatherService.getAllDataInMemory();
    }

}
