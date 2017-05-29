package com.infinote.differentthinking.infinote.config;

import com.infinote.differentthinking.infinote.config.base.ApiConstantsContract;

public class ApiConstants implements ApiConstantsContract {

    private static final String API_URL = "https://infinote.herokuapp.com/api";
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
    public String notesForCurrentUserUrl(String username) {
        return API_URL + "/users/" + username + "/notes";
    }

    @Override
    public String profilePictureUrl(String username) {
        return API_URL + "/users/" + username + "/profile";
    }

    @Override
    public String userPasswordUrl(String useranme) {
        return API_URL + "/users/" + useranme + "/password";
    }

    @Override
    public String updateNoteById(String id) {
        return API_URL + "/notes/" + id + "/update";
    }

    @Override
    public String deleteNoteUrl() {
        return API_URL + "/users/notes/delete";
    }

    @Override
    public String singleUserUrl(String username) {
        return API_URL + "/users/" + username;
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
