package org.demo.testinfra.llm;

import org.demo.testinfra.GsonProvider;
import org.demo.testinfra.Logger;
import org.demo.testinfra.apiutils.HttpClientProvider;
import org.demo.testinfra.apiutils.ResponseBodyHandler;
import org.demo.testinfra.config.LlmConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LlmGateway {

    private LlmConfig config;
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
        config = LlmConfig.getConfig();
        HttpClient client = HttpClientProvider.getClient();
        String url = "%s://%s:%d/%s".formatted(
                config.getApi().getScheme(),
                config.getApi().getHost(),
                config.getApi().getPort(),
                config.getApi().getEndpoint()
        );

        Logger.get().log(url);
        Logger.get().log(buildBody(prompt));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(buildBody(prompt)))
                .timeout(Duration.ofSeconds(config.getApi().getTimeoutSec()))
                .build();

        HttpResponse<OllamaApiResponsePojo> response;
        try {
            response =
                    client.send(request, new ResponseBodyHandler<>(OllamaApiResponsePojo.class));
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

    private String buildBody(String prompt){
        OllamaApiRequestPojo bodyObj = new OllamaApiRequestPojo();
        bodyObj.setModel(config.getApi().getModel());
        bodyObj.setPrompt(prompt);
        bodyObj.setStream(false);
        bodyObj.setNum_predict(config.getApi().getNumPredict());
        return GsonProvider.getGson().toJson(bodyObj);
    }
}
