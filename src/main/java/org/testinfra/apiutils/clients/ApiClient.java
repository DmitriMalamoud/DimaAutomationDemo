package org.testinfra.apiutils.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testinfra.Logger;
import org.testinfra.apiutils.BodyHandler;
import org.testinfra.config.TestEnvConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class ApiClient {

    private static HttpClient httpClient;
    @Autowired
    private TestEnvConfig env;

    // Constants
    private static final int TIMEOUT_SECONDS = 15;
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String URI_TEMPLATE = "%s://%s:%s/%s";

    protected <T> HttpResponse<T> sendApiRequest(String endpoint, String body, List<String> headers, Class<T> responseType)
            throws IOException, InterruptedException {

        URI uri =  URI.create(URI_TEMPLATE.formatted(env.getScheme(), env.getHost(), env.getPort(), endpoint));
        logApiRequest(uri.toString(), body, endpoint);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(uri)
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS));

        List<String> headerPairs = new ArrayList<>();
        headerPairs.add(CONTENT_TYPE);
        headerPairs.add(APPLICATION_JSON);

        // Support for API key header can be added here (env.getApiKey())

        if (headers != null && !headers.isEmpty()) {
            headerPairs.addAll(headers);
        }

        requestBuilder.headers(headerPairs.toArray(String[]::new));

        HttpRequest httpRequest = requestBuilder.build();

        HttpResponse<T> response = getClient()
                .send(httpRequest, new BodyHandler<>(responseType));
        logApiResponse(response.statusCode(), response.body().toString());
        return response;
    }

    private void logApiRequest(String uri, String body, String endpoint){
        Logger.get().log("API request to " + endpoint);
        Logger.get().log("URI: " + uri);
        Logger.get().log("Request body: " + body);
    }

    private void logApiResponse(int code, String body){
        Logger.get().log("Response status code: " + code);
        Logger.get().log("Response body: " + body);
    }

    private HttpClient getClient(){
        if(httpClient == null){
            httpClient = HttpClient.newHttpClient();
        }
        return httpClient;
    }
}
