package com.inshur.weather.service;

import com.inshur.weather.model.WeatherForecast;

public interface WeatherService {

    WeatherForecast getWeatherForecast(double lat, double lon);

}
