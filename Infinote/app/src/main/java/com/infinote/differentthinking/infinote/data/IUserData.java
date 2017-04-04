package com.infinote.differentthinking.infinote.data;

import com.infinote.differentthinking.infinote.models.IUser;

import io.reactivex.Observable;

public interface IUserData {

    Observable<IUser> signIn(String username, String password);

    Observable<IUser> signUp(String username, String password);
}

