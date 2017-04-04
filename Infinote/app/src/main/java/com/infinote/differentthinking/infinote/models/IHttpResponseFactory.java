package com.infinote.differentthinking.infinote.models;

import java.util.List;
import java.util.Map;

public interface IHttpResponseFactory {
    IHttpResponse createResponse(
            final Map<String, List<String>> headers, final String body,
            final String message, final int code);
}
