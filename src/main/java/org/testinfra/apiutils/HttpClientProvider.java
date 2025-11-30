package org.testinfra.apiutils;

import java.net.http.HttpClient;

public class HttpClientProvider {

    private static HttpClient client;

    public static HttpClient getClient(){
        if(client == null){
            client = HttpClient.newHttpClient();
        }
        return client;
    }
}
