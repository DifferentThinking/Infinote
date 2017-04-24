package com.infinote.differentthinking.infinote.utils.base;

import com.infinote.differentthinking.infinote.models.base.HttpResponseContract;

import io.reactivex.Observable;
import java.util.Map;

public interface OkHttpRequesterContract {

    Observable<HttpResponseContract> get(final String url);

    Observable<HttpResponseContract> get(final String url, final Map<String, String> headers);

    Observable<HttpResponseContract> post(final String url, final Map<String, String> body);

    Observable<HttpResponseContract> post(final String url, final Map<String, String> body, final Map<String, String> headers);
}
