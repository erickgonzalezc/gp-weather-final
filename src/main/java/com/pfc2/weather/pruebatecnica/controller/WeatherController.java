package com.pfc2.weather.pruebatecnica.controller;

import com.pfc2.weather.pruebatecnica.model.CordPayload;
import com.pfc2.weather.pruebatecnica.model.WeatherHistory;
import com.pfc2.weather.pruebatecnica.model.WeatherHistoryResponse;
import com.pfc2.weather.pruebatecnica.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class WeatherController {
    private final WeatherService weatherService;
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v1/weather")
    public ResponseEntity<Object> getWeatherInfoByCord(@RequestBody CordPayload payload){

        log.debug("Inside the weather endpoint with the following parameters: {}", payload);
        if (null == payload || null == payload.getLon()
                || null == payload.getLat()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(buildErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid Payload. Please make sure you have correct data."));
        }
        try{
            return ResponseEntity.ok(weatherService.getWeatherInformation(payload.getLat(), payload.getLon())) ;
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while processing the request."));
        }

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/weather/history")
    public ResponseEntity<Object> getAllHistoryWeatherData(){
        log.debug("Inside the history weather ");
        try{
            return ResponseEntity.ok(weatherService.getAllDataInMemory());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while processing the request."));
        }

    }
    private Map<String, Object> buildErrorResponse(int statusCode, String errorMessage) {
        return Map.of("code", statusCode, "errors", List.of(errorMessage));
    }


}
