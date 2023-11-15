package com.pfc2.weather.pruebatecnica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "weatherHistory")
public class WeatherHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double lat;
    private Double lon;
    private String weather;
    private Double tempMin;
    private Double tempMax;
    private Integer humidity;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
}
