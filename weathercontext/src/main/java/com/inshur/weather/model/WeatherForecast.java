package com.inshur.weather.model;

import java.util.List;

public class WeatherForecast {

    private List<DayForecast> days;

    public List<DayForecast> getDays() {
        return days;
    }

    public void setDays(List<DayForecast> days) {
        this.days = days;
    }

    public static class DayForecast {
        private long dateTimeUTC;

        private WeatherData weatherData;

        public long getDateTimeUTC() {
            return dateTimeUTC;
        }

        public void setDateTimeUTC(long dateTimeUTC) {
            this.dateTimeUTC = dateTimeUTC;
        }

        public WeatherData getWeatherData() {
            return weatherData;
        }

        public void setWeatherData(WeatherData weatherData) {
            this.weatherData = weatherData;
        }
    }

    public static class WeatherData {

        private float temperature;

        private int humidity;

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }
    }

}
