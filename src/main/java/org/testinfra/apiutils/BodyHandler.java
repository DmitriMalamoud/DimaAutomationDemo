package org.testinfra.apiutils;

import com.google.gson.JsonObject;
import org.testinfra.GsonHelper;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class BodyHandler<T> implements HttpResponse.BodyHandler<T> {
    private final Class<T> type;

    public BodyHandler(Class<T> type) {
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
                            return GsonHelper.getGson().fromJson(body, type);
                        }
                    }
                    return (T)GsonHelper.getGson().fromJson(body, JsonObject.class);
                }
        );
    }
}