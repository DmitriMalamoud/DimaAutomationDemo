package org.testinfra.apiutils.drivers;

import org.testinfra.TestConstants;
import org.testinfra.apiutils.BodyHandler;
import org.testinfra.apiutils.HttpClientHelper;
import org.testinfra.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class ApiDriver {
    protected int port;
    protected <T> HttpResponse<T> sendApiRequest(String endpoint, String body, List<String> headers, Class<T> responseType)
            throws IOException, InterruptedException, URISyntaxException {

        URI uri =  URI.create(String.format(TestConstants.BASE_API_URL, port) + endpoint);
        logApiRequest(uri.toString(), body);

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

        return HttpClientHelper.getClient()
                .send(httpRequest, new BodyHandler<>(responseType));
    }

    private void logApiRequest(String uri, String body){
        Logger.log("API request");
        Logger.log("URI: " + uri);
        Logger.log("Request body: " + body);
    }
}
