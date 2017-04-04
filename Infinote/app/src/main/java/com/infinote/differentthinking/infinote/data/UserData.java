package com.infinote.differentthinking.infinote.data;

import com.infinote.differentthinking.infinote.models.IUser;
import com.infinote.differentthinking.infinote.utils.OkHttpRequester;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserData implements IUserData {

    private final OkHttpRequester httpRequester;

    public UserData() {
        this.httpRequester = new OkHttpRequester();
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
