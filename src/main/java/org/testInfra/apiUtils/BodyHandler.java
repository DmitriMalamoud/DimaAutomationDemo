package org.testInfra.apiUtils;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class BodyHandler<T> implements HttpResponse.BodyHandler<T> {
    private final Class<T> type;

    public BodyHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(
                HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8),
                body -> GsonHelper.getGson().fromJson(body, type)
        );
    }
}