package org.callback;

import java.util.function.Consumer;

public interface CallbackListener<Response> {
    void onResponse(Response response);

    void onFailure(Exception e);


    static <Response> CallbackListener<Response> wrap(
            Consumer<Response> onResponse,
            Consumer<Exception> onFailure
            ) {
        return new CallbackListener<Response>() {
            @Override
            public void onResponse(Response response) {
                onResponse.accept(response);
            }

            @Override
            public void onFailure(Exception e) {
                onFailure.accept(e);
            }
        };
    }
}
