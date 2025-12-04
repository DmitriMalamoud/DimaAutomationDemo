package org.demo.testinfra.apiutils;

import com.google.gson.JsonObject;
import org.demo.testinfra.GsonProvider;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ResponseBodyHandler<T> implements HttpResponse.BodyHandler<T> {
    private final Class<T> type;

    public ResponseBodyHandler(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(
                HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8),
                body -> {
                    if(responseInfo.statusCode() == 200){
                        if(type == String.class){
                            return type.cast(body);
                        }
                        else{
                            return GsonProvider.getGson().fromJson(body, type);
                        }
                    }
                    return (T) GsonProvider.getGson().fromJson(body, JsonObject.class);
                }
        );
    }
}