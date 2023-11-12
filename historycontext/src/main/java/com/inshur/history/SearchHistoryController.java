package com.inshur.history;

import com.inshur.shared.datalayer.WeatherRequestHistoryRepository;
import com.inshur.shared.model.WeatherRequestHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/history")
public class SearchHistoryController {

    @Autowired
    private WeatherRequestHistoryRepository weatherRequestHistoryDataRepository;

    @GetMapping(value = "/search/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeatherRequestHistory>> searchWeatherHistory(
            @RequestParam(name = "lat", required = false) Double lat,
            @RequestParam(name = "lon", required = false) Double lon,
            @RequestParam(name = "sortBy", required = false, defaultValue = "dt") String sortBy,
            @RequestParam(name = "asc", required = false, defaultValue = "true") boolean isAsc,
            @RequestParam(name = "limit", required = false) Integer limit) {

        // This is not optimal approach, but with 2 hours limit propagate proper permanent storage is overkill
        List<WeatherRequestHistory> withFilters = weatherRequestHistoryDataRepository.findWithFilters(lat, lon, sortBy, isAsc, limit);
        return ResponseEntity.status(HttpStatus.OK).body(withFilters);
    }
}
