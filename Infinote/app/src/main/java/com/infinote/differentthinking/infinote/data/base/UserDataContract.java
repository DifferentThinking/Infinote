package com.infinote.differentthinking.infinote.data.base;

import com.infinote.differentthinking.infinote.models.base.UserContract;

import io.reactivex.Observable;

public interface UserDataContract {

    Observable<UserContract> signIn(String username, String password);

    Observable<UserContract> signUp(String username, String password);
}

