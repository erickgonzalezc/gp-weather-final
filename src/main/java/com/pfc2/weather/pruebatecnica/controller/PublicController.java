package com.pfc2.weather.pruebatecnica.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "sys")
@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class PublicController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/status")
    public void getStatus() {
        log.info("you are into the public endpoint and you don't need authentication");
    }
}
