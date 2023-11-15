package com.pfc2.weather.pruebatecnica.adapter;

import com.pfc2.weather.pruebatecnica.model.WeatherData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherServiceAdapter {

    private final WebClient webClient;

    @Value("${application.weather.app-id}")
    private String appId;
    public WeatherData getWeatherDataFromApi(double lat, double lon){
        log.info("You are in the adapter and you will call the weather endpoint with the following parameters: lat {}, lon {}", lat, lon);
        try{
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("appid", appId)
                            .build())
                    .retrieve()

                    .bodyToMono(WeatherData.class)
                    .block();

        } catch (WebClientResponseException e) {
            int statusCode = e.getRawStatusCode();
            String statusText = e.getStatusText();
            String responseBody = e.getResponseBodyAsString();

            log.error("Error:{} - {}",  statusCode , statusText);
            log.error("Response body: {} " , responseBody);
            return null;
        }



    }
    public Mono<? extends Throwable> handle4xxError(ClientResponse response) {
        if (response.statusCode().is4xxClientError()) {
            // Read the error message from the response body
            return response.bodyToMono(String.class)
                    .flatMap(s -> {
                        if (response.statusCode().equals(HttpStatus.BAD_REQUEST)) {
                            // Log the error message for 400 Bad Request
                            log.error("400 Bad Request received from the API: {}", s);
                            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,s));
                        } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                            // Log the error message for 404 Not Found
                            log.error("404 Not Found Request from the API: {}", s);
                            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,s));
                        } else {
                            // Log the error message for other 4xx errors
                            log.error("4xx Error received from the API: {}", s);
                        }
                        return Mono.empty();
                    });
        }
        return Mono.error(new RuntimeException("Error occurred during API request"));
    }
}
