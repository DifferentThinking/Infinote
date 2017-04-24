package com.infinote.differentthinking.infinote.utils;

import android.util.Log;

import com.infinote.differentthinking.infinote.models.HttpResponseFactory;
import com.infinote.differentthinking.infinote.models.IHttpResponse;
import com.infinote.differentthinking.infinote.models.IHttpResponseFactory;

import java.io.IOException;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.internal.observers.LambdaObserver;
import okhttp3.*;

        import java.util.Map;
        import java.util.concurrent.Callable;

public class OkHttpRequester implements IHttpRequester {

    private final IHttpResponseFactory responseFactory;
    private final OkHttpClient httpClient;

    public OkHttpRequester() {
        this.responseFactory = new HttpResponseFactory();
        this.httpClient = new OkHttpClient();
    }

    public Observable<IHttpResponse> get(final String url) {
        return Observable.defer(new Callable<ObservableSource<? extends IHttpResponse>>() {
            @Override
            public ObservableSource<? extends IHttpResponse> call() throws Exception {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                return createResponse(request);
            }
        });
    }

    public Observable<IHttpResponse> get(final String url, final Map<String, String> headers) {
        return Observable.defer(new Callable<ObservableSource<? extends IHttpResponse>>() {
            @Override
            public ObservableSource<? extends IHttpResponse> call() throws Exception {
                Request.Builder requestBuilder = new Request.Builder()
                        .url(url);

                for (Map.Entry<String, String> pair : headers.entrySet()) {
                    requestBuilder.addHeader(pair.getKey(), pair.getValue());
                }

                Request request = requestBuilder.build();
                return createResponse(request);
            }
        });
    }

    public Observable<IHttpResponse> post(final String url, final Map<String, String> body) {
        return Observable.defer(new Callable<ObservableSource<? extends IHttpResponse>>() {
            @Override
            public ObservableSource<? extends IHttpResponse> call() throws Exception {
                RequestBody requestBody = createRequestBody(body);

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                return createResponse(request);
            }
        });
    }

    public Observable<IHttpResponse> post(final String url, final Map<String, String> body,
                                          final Map<String, String> headers) {

        return Observable.defer(new Callable<ObservableSource<? extends IHttpResponse>>() {
            @Override
            public ObservableSource<? extends IHttpResponse> call() throws Exception {
                RequestBody requestBody = createRequestBody(body);

                Request.Builder requestBuilder = new Request.Builder()
                        .url(url)
                        .post(requestBody);

                for (Map.Entry<String, String> pair : headers.entrySet()) {
                    requestBuilder.addHeader(pair.getKey(), pair.getValue());
                }

                Request request = requestBuilder.build();
                return createResponse(request);
            }
        });
    }

    private RequestBody createRequestBody(Map<String, String> bodyMap) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();

        for (Map.Entry<String, String> pair : bodyMap.entrySet()) {
            bodyBuilder.add(pair.getKey(), pair.getValue());
        }

        RequestBody requestBody = bodyBuilder.build();
        return requestBody;
    }

    private Observable<IHttpResponse> createResponse(Request request) {
        try {
            Response response = httpClient.newCall(request).execute();

            IHttpResponse responseParsed = responseFactory.createResponse(
                    response.headers().toMultimap(), response.body().string(),
                    response.message(), response.code());
            Log.d("HERE: ", response.message());
            return Observable.just(responseParsed);
        } catch (IOException e) {
            Log.d("HERE: ", e.getMessage());
            return Observable.error(e);
        }
    }
}