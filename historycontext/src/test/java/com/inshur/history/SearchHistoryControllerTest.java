package com.inshur.history;


import com.inshur.shared.datalayer.WeatherRequestHistoryRepository;
import com.inshur.shared.model.WeatherRequestHistory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SearchHistoryControllerTest.class)
@org.springframework.test.context.BootstrapWith(value = org.springframework.boot.test.context.SpringBootTestContextBootstrapper.class)
@WebMvcTest(SearchHistoryController.class)
@ComponentScan("com.inshur.history")
public class SearchHistoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherRequestHistoryRepository weatherRequestHistoryRepository;

    @Test
    public void testSanityOk() throws Exception {

        // Given
        List<WeatherRequestHistory> content = new ArrayList<>();
        content.add(new WeatherRequestHistory(null, -1, -1, "OK"));

        given(weatherRequestHistoryRepository
                .findWithFilters(null, null, "dt", true, null))
                .willReturn(content);

        // When // Then
        mvc.perform(MockMvcRequestBuilders.get("/history/search/weather").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"requestDateTime\":null,\"lat\":-1.0,\"lon\":-1.0,\"status\":\"OK\"}]")));

        Mockito.verify(weatherRequestHistoryRepository, Mockito.times(1))
                .findWithFilters(any(), any(), any(), anyBoolean(), any());
    }

}
