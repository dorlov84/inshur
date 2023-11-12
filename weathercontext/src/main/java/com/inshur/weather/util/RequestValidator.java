package com.inshur.weather.util;

import com.inshur.weather.service.CountryService;
import org.assertj.core.util.VisibleForTesting;

// TODO: think about move this with level of abstraction like ValidatorStrategy which can add specific validators and execute one after another
// Kept as is for sake of time
public class RequestValidator {

    private final CountryService countryService;

    public RequestValidator(CountryService countryService) {
        this.countryService = countryService;
    }

    public String validateWarmestDayRequest(double lat, double lon)
    {
        if (!validateLat(lat)) {
            return "Incorrect format for GET lat parameter: it should be in range [-90, 90] and has no more than 6 decimals";
        }

        if (!validateLon(lon)) {
            return "Incorrect format for GET lat parameter: it should be in range [-180, 180) and has no more than 6 decimals";
        }

        if (!validateCountryByLatLon(lat, lon)) {
            return "Sorry, location is outside of the United Kingdom of Great Britain and Northern Ireland";
        }

        return null;
    }

    @VisibleForTesting
    protected boolean validateLat(double lat) {

        if (lat < -90 || lat > 90) {
            return false;
        }

        if (getDecimalsCount(lat) >= 6) {
            return false;
        }

        return true;
    }

    @VisibleForTesting
    protected boolean validateLon(double lon) {
        if (lon < -180 || lon >= 180) {
            return false;
        }

        // Use as property or constant
        if (getDecimalsCount(lon) >= 6) {
            return false;
        }

        return true;
    }

    @VisibleForTesting
    protected boolean validateCountryByLatLon(double lat, double lon) {

        String countryName = countryService.getCountry2LettersCode(lat, lon);

        // Pass as config
        return "GB".equalsIgnoreCase(countryName);
    }

    @VisibleForTesting
    protected static int getDecimalsCount(double num) {

        String numAsStr = Double.toString(num);
        if(numAsStr.contains(".")) {
            String s = numAsStr.replaceAll(".*\\.(?=\\d?)", "");
            return s.length();
        }

        return 0;
    }
}
