package org.testinfra.llm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testinfra.apiutils.HttpClientProvider;
import org.testinfra.apiutils.ResponseBodyHandler;
import org.testinfra.config.LlmConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LlmGateway {

    private LlmConfig config; //todo
    private static LlmGateway instance;

    private LlmGateway(){
    }

    public static LlmGateway get(){
        if(instance == null){
            instance = new LlmGateway();
        }
        return instance;
    }

    public String getLlmResponse(String prompt) {
        HttpClient client = HttpClientProvider.getClient();
        String url = "%s://%s:%d/%s".formatted(
                config.getApi().getScheme(),
                config.getApi().getHost(),
                config.getApi().getPort(),
                config.getApi().getEndpoint()
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(prompt))
                .timeout(Duration.ofMinutes(1))
                .build();

        HttpResponse<LlmResponsePojo> response;
        try {
            response =
                    client.send(request, new ResponseBodyHandler<>(LlmResponsePojo.class));
        } catch (Exception e) {
            return "Error communicating with LLM service: " + e.getMessage();
        }
        if (response.statusCode() != 200) {
            return "LLM service returned error status: " + response.statusCode();
        }
        if(null == response.body()){
            return "LLM service returned empty response body.";
        }
        return response.body().getResponse();
    }
}
