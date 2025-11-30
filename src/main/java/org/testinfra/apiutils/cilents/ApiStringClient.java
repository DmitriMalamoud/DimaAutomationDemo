package org.testinfra.apiutils.cilents;

import com.google.gson.JsonObject;
import org.testinfra.GsonProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

public class ApiStringClient extends ApiClient {
    public ApiStringClient(int port){
        this.port = port;
    }

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
