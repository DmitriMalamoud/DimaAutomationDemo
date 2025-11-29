package org.testInfra.apiUtils;

import java.net.http.HttpClient;

public class HttpClientHelper {

    private static HttpClient client;

    public static HttpClient getClient(){
        if(client == null){
            client = HttpClient.newHttpClient();
        }
        return client;
    }
}
