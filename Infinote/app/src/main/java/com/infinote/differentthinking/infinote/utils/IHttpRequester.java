package com.infinote.differentthinking.infinote.utils;

import com.infinote.differentthinking.infinote.models.IHttpResponse;

import io.reactivex.Observable;
import java.util.Map;

public interface IHttpRequester {

    Observable<IHttpResponse> get(final String url);

    Observable<IHttpResponse> get(final String url, final Map<String, String> headers);

    Observable<IHttpResponse> post(final String url, final Map<String, String> body);

    Observable<IHttpResponse> post(final String url, final Map<String, String> body, final Map<String, String> headers);
}
