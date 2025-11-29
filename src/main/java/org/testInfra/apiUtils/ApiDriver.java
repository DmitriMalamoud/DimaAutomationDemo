package org.testInfra.apiUtils;

import org.testInfra.TestConstants;
import org.testInfra.loggingUtils.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public abstract class ApiDriver {
    public<T> HttpResponse<T> sendApiRequest(String endpoint, String body, List<String> headers, Class<T> responseType)
            throws IOException, InterruptedException, URISyntaxException {

        URI uri = new URI(TestConstants.BASE_API_URL + endpoint);
        logApiRequest(uri.toString(), body);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(uri)
                .headers(Optional.ofNullable(headers)
                        .map(l -> l.toArray(String[]::new))
                        .orElse(null))
                .build();

        return HttpClientHelper.getClient()
                .send(httpRequest, new BodyHandler<>(responseType));
    }

    protected String buildRequestBody(String text){
        return buildRequestBody(text, null);
    }

    protected String buildRequestBody(String text, String subtext){
        ApiRequestTextBody requestBody = new ApiRequestTextBody(text, subtext);
        return GsonHelper.getGson().toJson(requestBody);
    }

    private void logApiRequest(String uri, String body){
        Logger.log("API request");
        Logger.log("URI: " + uri);
        Logger.log("Request body: " + body);
    }
}
