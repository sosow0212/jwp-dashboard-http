package org.apache.coyote.http11.handler.mapper.controller;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public interface Controller {

    HttpResponse handle(HttpRequest request) throws Exception;
}
