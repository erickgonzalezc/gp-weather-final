package com.pfc2.weather.pruebatecnica.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
    private String code;
    private List<String> errors;
}
