package com.pfc2.weather.pruebatecnica.repository;

import com.pfc2.weather.pruebatecnica.model.WeatherHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Optional;

@Repository
public interface WeatherHistoryRepository extends JpaRepository<WeatherHistory, Long> {

    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    @Query(value = "SELECT wh FROM WeatherHistory  wh WHERE wh.lat = :lat AND wh.lon = :lon " +
            "AND wh.created >= :tenMinutesAgo")
    Optional<WeatherHistory> findByLatAndLon(@Param("lat") Double lat, @Param("lon") Double lon,
                                             @Param("tenMinutesAgo") Date tenMinutesAgo);
}
