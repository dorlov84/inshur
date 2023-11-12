package com.inshur.application;

import com.inshur.history.SearchHistoryController;
import com.inshur.shared.datalayer.DataRepository;
import com.inshur.shared.datalayer.WeatherRequestHistoryRepository;
import com.inshur.shared.model.WeatherRequestHistory;
import com.inshur.shared.net.HttpManager;
import com.inshur.shared.net.HttpManagerImpl;
import com.inshur.weather.WeatherController;
import com.inshur.weather.service.CountryService;
import com.inshur.weather.service.OpenApiCountryService;
import com.inshur.weather.service.OpenApiWeatherService;
import com.inshur.weather.service.WeatherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({ WeatherController.class, SearchHistoryController.class })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WeatherService WeatherServiceBean() {
        return new OpenApiWeatherService();
    }

    @Bean
    public CountryService CountryServiceBean() {
        return new OpenApiCountryService();
    }

    @Bean
    public HttpManager HttpManagerBean() {
        return new HttpManagerImpl();
    }

    @Bean
    public DataRepository<WeatherRequestHistory> WeatherRequestHistoryRepositoryBean() {
        return new WeatherRequestHistoryRepository();
    }

}
