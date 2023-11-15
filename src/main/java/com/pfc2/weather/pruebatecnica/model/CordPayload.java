package com.pfc2.weather.pruebatecnica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CordPayload {
    private Double lat;
    private Double lon;
}
