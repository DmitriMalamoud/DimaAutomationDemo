package org.demo.testinfra.apiutils;

import java.net.http.HttpClient;

public class HttpClientProvider {
    private static HttpClient httpClient;

    public static HttpClient getClient(){
        if(httpClient == null){
            httpClient = HttpClient.newHttpClient();
        }
        return httpClient;
    }
}
