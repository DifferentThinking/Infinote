package com.infinote.differentthinking.infinote.models;

import java.util.List;
import java.util.Map;

public class HttpResponseFactory implements IHttpResponseFactory {

    public IHttpResponse createResponse(
            final Map<String, List<String>> headers, final String body,
            final String message, final int code) {

        return new IHttpResponse() {
            @Override
            public Map<String, List<String>> getHeaders() {
                return headers;
            }

            @Override
            public String getBody() {
                return body;
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public int getCode() {
                return code;
            }
        };
    }
}
