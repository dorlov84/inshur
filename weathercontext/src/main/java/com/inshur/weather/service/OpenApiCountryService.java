package com.inshur.weather.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inshur.shared.net.HttpManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenApiCountryService implements CountryService {

    @Autowired
    private HttpManager httpManager;

    @Override
    public String getCountry2LettersCode(double lat, double lon) {
        try {

            // Pass url as config
            OpenApiCountry[] openApiCountry = httpManager.doGet(
                    "http://api.openweathermap.org/geo/1.0/reverse?limit=1&lat=" + lat + "&lon=" + lon + "&appid=91a5e3d94708c57e8248a454d817a493",
                    OpenApiCountry[].class);

            return openApiCountry == null || openApiCountry.length == 0 ? null : openApiCountry[0].country;
        }  catch (Exception e) {
            // Ignoring for sake of time, need to pass to upstream and address, now we swallow and return
        }

        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OpenApiCountry {

        @JsonProperty("country")
        private String country;
    }
}
