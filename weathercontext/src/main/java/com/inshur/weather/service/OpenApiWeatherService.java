package com.inshur.weather.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inshur.shared.net.HttpManager;
import com.inshur.weather.model.WeatherForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OpenApiWeatherService implements WeatherService {

    @Autowired
    private HttpManager httpManager;

    @Override
    public WeatherForecast getWeatherForecast(double lat, double lon) {

        try {
            // Pass url + secret as config + key vault resource
            return convertToInternalContract(httpManager.doGet(
                    "http://api.openweathermap.org/data/2.5/forecast?units=metric&lat=" + lat + "&lon=" + lon + "&appid=91a5e3d94708c57e8248a454d817a493",
                    OpenApiWeatherForecast.class));
        }  catch (Exception e) {
            // Ignoring for sake of time, need to pass to upstream and address, now we swallow and return
        }

        return null;
    }

    private WeatherForecast convertToInternalContract(OpenApiWeatherForecast forecast) {

        WeatherForecast internal = new WeatherForecast();
        if (forecast == null) {
            return internal;
        }

        List<WeatherForecast.DayForecast> days = new LinkedList<>();
        for (OpenApiWeatherPoint point : forecast.list) {

            WeatherForecast.WeatherData weatherData = new WeatherForecast.WeatherData();
            weatherData.setHumidity(point.weatherData.humidity);
            weatherData.setTemperature(point.weatherData.temperature);

            WeatherForecast.DayForecast dayForecast = new WeatherForecast.DayForecast();
            dayForecast.setDateTimeUTC(point.dateTimeUTC);
            dayForecast.setWeatherData(weatherData);

            days.add(dayForecast);
        }

        internal.setDays(days);
        return internal;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OpenApiWeatherForecast {

        @JsonProperty("list")
        private List<OpenApiWeatherPoint> list;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OpenApiWeatherPoint {
        @JsonProperty("dt")
        private long dateTimeUTC;

        @JsonProperty("main")
        private OpenApiWeatherData weatherData;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OpenApiWeatherData {

        @JsonProperty("temp")
        private float temperature;

        @JsonProperty("humidity")
        private int humidity;
    }
}
