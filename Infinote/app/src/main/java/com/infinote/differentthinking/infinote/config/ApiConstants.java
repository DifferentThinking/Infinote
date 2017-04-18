package com.infinote.differentthinking.infinote.config;

public class ApiConstants implements IApiConstants{

    private static final String API_URL = "https://infinote.herokuapp.com";
    //private static final String API_URL = "http://10.0.2.2:8080";
    private static final String URL_SIGN_IN = API_URL + "/auth/login";
    private static final String URL_SIGN_UP = API_URL + "/auth/register";

    private static final int RESPONSE_SUCCESS_CODE = 200;
    private static final int RESPONSE_ERROR_CODE = 404;

    @Override
    public String signInUrl() {
        return URL_SIGN_IN;
    }

    @Override
    public String signUpUrl() {
        return URL_SIGN_UP;
    }

    @Override
    public int responseSuccessCode() {
        return RESPONSE_SUCCESS_CODE;
    }

    @Override
    public int responseErrorCode() {
        return RESPONSE_ERROR_CODE;
    }
}
