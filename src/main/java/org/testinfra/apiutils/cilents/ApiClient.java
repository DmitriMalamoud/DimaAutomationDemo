package org.testinfra.apiutils.cilents;

import org.testinfra.Logger;
import org.testinfra.TestConstants;
import org.testinfra.apiutils.BodyHandler;
import org.testinfra.apiutils.HttpClientProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class ApiClient {
    protected int port;
    protected <T> HttpResponse<T> sendApiRequest(String endpoint, String body, List<String> headers, Class<T> responseType)
            throws IOException, InterruptedException {

        URI uri =  URI.create(String.format(TestConstants.BASE_API_URL, port) + endpoint);
        logApiRequest(uri.toString(), body, endpoint);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(uri)
                .timeout(Duration.ofSeconds(15));

        List<String> headerPairs = new ArrayList<>();
        headerPairs.add("Content-Type");
        headerPairs.add("application/json");
        if (headers != null && !headers.isEmpty()) {
            headerPairs.addAll(headers);
        }
        requestBuilder.headers(headerPairs.toArray(String[]::new));

        HttpRequest httpRequest = requestBuilder.build();

        HttpResponse<T> response = HttpClientProvider.getClient()
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
}
