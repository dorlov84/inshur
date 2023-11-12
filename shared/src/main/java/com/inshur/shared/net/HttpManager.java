package com.inshur.shared.net;

import java.io.IOException;

public interface HttpManager {

    <T> T doGet(String url, Class<T> ofType) throws IOException;
   // <T> T[] doGet(String url, Class<T>[] ofType) throws IOException;
}
