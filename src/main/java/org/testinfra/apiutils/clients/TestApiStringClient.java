package org.testinfra.apiutils.clients;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import org.testinfra.GsonProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

@Component
public class TestApiStringClient extends TestApiClient {

    public HttpResponse<String> sendTextMirrorRequest(String text) throws IOException, InterruptedException, URISyntaxException {
       return sendApiRequest("string-mirror", buildBodyJson(text), null, String.class);
    }

    public HttpResponse<Integer> sendTextCountRequest(String text) throws IOException, InterruptedException, URISyntaxException {
        return sendApiRequest("string-count", buildBodyJson(text), null, Integer.class);
    }

    public HttpResponse<Boolean> sendTextContainsRequest(String text, String subtext) throws IOException, InterruptedException, URISyntaxException {
        return sendApiRequest("string-contains", buildBodyJson(text, subtext), null, Boolean.class);
    }

    public HttpResponse<Boolean> sendTextContainsRequest(String text) throws IOException, InterruptedException, URISyntaxException {
        return sendTextContainsRequest(text, null);
    }

    private String buildBodyJson(String text){
        return buildBodyJson(text, null);
    }

    private String buildBodyJson(String text, String subtext){
        JsonObject root = new JsonObject();
        root.addProperty("text", text);
        root.addProperty("subtext", subtext);
        return GsonProvider.getGson().toJson(root);
    }
}
