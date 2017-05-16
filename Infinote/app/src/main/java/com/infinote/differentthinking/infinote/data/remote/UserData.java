package com.infinote.differentthinking.infinote.data.remote;

import android.content.Context;
import android.util.Log;

import com.infinote.differentthinking.infinote.config.ApiConstants;
import com.infinote.differentthinking.infinote.data.remote.base.UserDataContract;
import com.infinote.differentthinking.infinote.models.base.HttpResponseContract;
import com.infinote.differentthinking.infinote.models.base.UserContract;
import com.infinote.differentthinking.infinote.models.User;
import com.infinote.differentthinking.infinote.utils.GsonParser;
import com.infinote.differentthinking.infinote.utils.HashProvider;
import com.infinote.differentthinking.infinote.utils.OkHttpRequester;
import com.infinote.differentthinking.infinote.data.local.UserSession;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserData implements UserDataContract {
    private final OkHttpRequester httpRequester;
    private final HashProvider hashProvider;
    private final ApiConstants apiConstants;
    private final GsonParser jsonParser;
    private final UserSession userSession;
    private final Type userModelType;

    public UserData(Context context) {
        this.jsonParser = new GsonParser();
        this.hashProvider = new HashProvider();
        this.httpRequester = new OkHttpRequester();
        this.apiConstants = new ApiConstants();
        this.userSession = new UserSession(context);
        this.userModelType = User.class;
    }

    @Override
    public Observable<UserContract> signIn(String username, String password) {
        Map<String, String> userCredentials = new HashMap<>();
        String passHash = hashProvider.hashPassword(password);
        userCredentials.put("username", username.toLowerCase());
        userCredentials.put("passHash", passHash);

        return httpRequester
                .post(apiConstants.signInUrl(), userCredentials)
                .map(new Function<HttpResponseContract, UserContract>() {
                    @Override
                    public UserContract apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }
                        String responseBody = iHttpResponse.getBody().toString();
                        String userJson = jsonParser.getDirectMember(responseBody, "result");
                        UserContract resultUser = jsonParser.fromJson(userJson, userModelType);

                        userSession.setUsername(resultUser.getUsername());
                        //userSession.setId(resultUser.getId());
                        return resultUser;
                    }
                });
    }

    //String username, String email, String firstname, String lastname, String password
    @Override
    public Observable<UserContract> signUp(String username, String email, String firstname, String lastname, String password) {
        Map<String, String> userCredentials = new HashMap<>();
        String passHash = hashProvider.hashPassword(password);
        userCredentials.put("username", username.toLowerCase());
        userCredentials.put("email", email);
        userCredentials.put("firstname", firstname);
        userCredentials.put("lastname", lastname);
        userCredentials.put("passHash", passHash);

        return httpRequester
                .post(apiConstants.signUpUrl(), userCredentials)
                .map(new Function<HttpResponseContract, UserContract>() {
                    @Override
                    public UserContract apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody().toString();

                        String userJson = jsonParser.getDirectMember(responseBody, "result");
                        UserContract resultUser = jsonParser.fromJson(userJson, userModelType);

                        return resultUser;
                    }
                });
    }

    public Observable<UserContract> getInfoForCurrentUser() {
        return httpRequester
                .get(apiConstants.singleUserUrl(userSession.getUsername()))
                .map(new Function<HttpResponseContract, UserContract>() {
                    @Override
                    public UserContract apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }
                        String responseBody = iHttpResponse.getBody().toString();
                        String userJson = jsonParser.getDirectMember(responseBody, "result");
                        Log.d("HALO", userJson) ;
                        UserContract resultUser = jsonParser.fromJson(userJson, userModelType);

                        return resultUser;
                    }
                });
    }

    public void logoutUser() {
        this.userSession.clearSession();
    }

    public boolean isLoggedIn() {
        return this.userSession.isUserLoggedIn();
    }
}
