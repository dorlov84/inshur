package com.inshur.weather;

import com.inshur.shared.datalayer.DataRepository;
import com.inshur.shared.model.WeatherRequestHistory;
import com.inshur.weather.model.WeatherForecast;
import com.inshur.weather.service.CountryService;
import com.inshur.weather.service.WeatherService;
import com.inshur.weather.util.RequestValidator;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/weather")
public class WeatherController {

   @Autowired
   private WeatherService weatherService;

   @Autowired
   private CountryService countryService;

   @Autowired
   private DataRepository<WeatherRequestHistory> weatherRequestHistoryDataRepository;

    @GetMapping("/warmestDay")
    public ResponseEntity getBestDay(@RequestParam(name="lat") double lat, @RequestParam(name="lon") double lon) {

        ResponseEntity responseEntity = null;
        try {
            String validationResult = new RequestValidator(countryService).validateWarmestDayRequest(lat, lon);
            if (validationResult != null) {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationResult);
            } else {
                WeatherForecast weatherForecast = weatherService.getWeatherForecast(lat, lon);
                responseEntity = weatherForecast != null
                        ? ResponseEntity.status(HttpStatus.OK).body(pickWarmestDay(weatherForecast))
                        : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry, something went wrong, please try later");
            }
        } finally {
            weatherRequestHistoryDataRepository.save(
                    new WeatherRequestHistory(new Date(), lat, lon, responseEntity == null ? "UNKNOWN_ERROR" : responseEntity.getStatusCode().toString()));
        }

        return responseEntity;
    }

    @VisibleForTesting
    private WeatherForecast.DayForecast pickWarmestDay(WeatherForecast forecast) {

        WeatherForecast.DayForecast bestDay = null;
        if (forecast == null) {
            return bestDay;
        }

        for (WeatherForecast.DayForecast day : forecast.getDays()) {
            if (bestDay == null) {
                bestDay = day;
            } else {
                if (day.getWeatherData().getTemperature() >= bestDay.getWeatherData().getTemperature()) {
                    if (day.getWeatherData().getHumidity() < bestDay.getWeatherData().getHumidity()) {
                        bestDay = day;
                    }
                }
            }
        }

        return bestDay;
    }

}
