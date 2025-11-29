package org.testinfra.apiutils.drivers;

import com.google.gson.JsonObject;
import org.testinfra.GsonHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

public class ApiTextDriver extends ApiDriver {
    public ApiTextDriver(int port){
        this.port = port;
    }

    public HttpResponse<String> sendTextMirrorRequest(String text) throws IOException, InterruptedException, URISyntaxException {
       return sendApiRequest("string-mirror", buildBodyJson(text), null, String.class);
    }

    public HttpResponse<Integer> sendTextCountRequest(String text) throws IOException, InterruptedException, URISyntaxException {
        return sendApiRequest("string-count", buildBodyJson(text), null, Integer.class);
    }

    public HttpResponse<Boolean> sendTextContainsRequest(String text) throws IOException, InterruptedException, URISyntaxException {
        return sendApiRequest("string-contains", buildBodyJson(text), null, Boolean.class);
    }

    private String buildBodyJson(String text){
        return buildBodyJson(text, null);
    }

    private String buildBodyJson(String text, String subtext){
        JsonObject root = new JsonObject();
        root.addProperty("text", text);
        root.addProperty("subtext", subtext);
        return GsonHelper.getGson().toJson(root);
    }
}
