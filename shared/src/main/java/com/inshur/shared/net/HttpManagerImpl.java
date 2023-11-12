package com.inshur.shared.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpManagerImpl implements HttpManager {
    @Override
    public <T> T doGet(String url, Class<T> ofType) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        InputStream responseStream = connection.getInputStream();

        return toSupplierOfType(responseStream, ofType);
    }

    private static <T> T toSupplierOfType(InputStream inputStream, Class<T> targetType) {
            try (InputStream stream = inputStream) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(stream, targetType);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
    }
}
