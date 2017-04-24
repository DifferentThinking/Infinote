package com.infinote.differentthinking.infinote.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.infinote.differentthinking.infinote.config.ApiConstants;
import com.infinote.differentthinking.infinote.models.IHttpResponse;
import com.infinote.differentthinking.infinote.models.IUser;
import com.infinote.differentthinking.infinote.models.User;
import com.infinote.differentthinking.infinote.utils.GsonParser;
import com.infinote.differentthinking.infinote.utils.HashProvider;
import com.infinote.differentthinking.infinote.utils.OkHttpRequester;
import com.infinote.differentthinking.infinote.utils.UserSession;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserData implements IUserData {

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
    public Observable<IUser> signIn(String username, String password) {
        Map<String, String> userCredentials = new HashMap<>();
        String passHash = hashProvider.hashPassword(password);
        userCredentials.put("username", username.toLowerCase());
        userCredentials.put("passHash", passHash);

        return httpRequester
                .post(apiConstants.signInUrl(), userCredentials)
                .map(new Function<IHttpResponse, IUser>() {
                    @Override
                    public IUser apply(IHttpResponse iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }
                        String responseBody = iHttpResponse.getBody().toString();
                        String userJson = jsonParser.getDirectMember(responseBody, "result");
                        IUser resultUser = jsonParser.fromJson(userJson, userModelType);

                        userSession.setUsername(resultUser.getUsername());
                        userSession.setId(resultUser.getId());
                        return resultUser;
                    }
                });
    }

    @Override
    public Observable<IUser> signUp(String username, String password) {
        Map<String, String> userCredentials = new HashMap<>();
        String passHash = hashProvider.hashPassword(password);
        userCredentials.put("username", username.toLowerCase());
        userCredentials.put("passHash", passHash);

        return httpRequester
                .post(apiConstants.signUpUrl(), userCredentials)
                .map(new Function<IHttpResponse, IUser>() {
                    @Override
                    public IUser apply(IHttpResponse iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }

                        String responseBody = iHttpResponse.getBody().toString();
                        String userJson = jsonParser.getDirectMember(responseBody, "result");
                        IUser resultUser = jsonParser.fromJson(userJson, userModelType);

                        return resultUser;
                    }
                });
    }

}
