package com.inshur.weather.util;


import com.inshur.weather.service.CountryService;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestValidatorTest {

    @Test
    public void testValidateIncorrectCountry() {

        // Given
        CountryService countryService = Mockito.mock(CountryService.class);
        Mockito.when(countryService.getCountry2LettersCode(Mockito.eq(-1d), Mockito.eq(-1d))).thenReturn("IE");

        RequestValidator requestValidator = new RequestValidator(countryService);

        // When
        boolean result = requestValidator.validateCountryByLatLon(-1d, -1d);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    public void testValidateCorrectCountry() {

        // Given
        CountryService countryService = Mockito.mock(CountryService.class);
        Mockito.when(countryService.getCountry2LettersCode(Mockito.eq(-1d), Mockito.eq(-1d))).thenReturn("GB");

        RequestValidator requestValidator = new RequestValidator(countryService);

        // When
        boolean result = requestValidator.validateCountryByLatLon(-1d, -1d);

        // Then
        assertThat(result).isTrue();
    }
}
