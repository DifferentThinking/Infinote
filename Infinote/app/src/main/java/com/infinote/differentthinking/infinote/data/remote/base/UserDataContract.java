package com.infinote.differentthinking.infinote.data.remote.base;

import com.infinote.differentthinking.infinote.models.base.UserContract;

import io.reactivex.Observable;

public interface UserDataContract {

    Observable<UserContract> signIn(String username, String password);

    Observable<UserContract> signUp(String username, String email, String firstname, String lastname, String password);

    Observable<UserContract> getInfoForCurrentUser();

    void logoutUser();

    boolean isLoggedIn();
}

