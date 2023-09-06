package org.apache.coyote.http11.handler.adapter;

import org.apache.coyote.http11.handler.mapper.controller.Controller;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class HandlerAdapter {

    private HandlerAdapter() {
    }

    public static HttpResponse adaptController(final Controller controller, final HttpRequest httpRequest) throws Exception {
        return controller.handle(httpRequest);
    }
}
