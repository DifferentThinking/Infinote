package com.infinote.differentthinking.infinote.models.base;

import com.infinote.differentthinking.infinote.models.base.HttpResponseContract;

import java.util.List;
import java.util.Map;

public interface HttpResponseFactoryContract {
    HttpResponseContract createResponse(
            final Map<String, List<String>> headers, final String body,
            final String message, final int code);
}
