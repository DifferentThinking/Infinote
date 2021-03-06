package com.infinote.differentthinking.infinote.views.auth.login;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.models.base.UserContract;
import com.infinote.differentthinking.infinote.views.auth.login.base.LoginContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {
    private UserData userData;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

    @Override
    public void loginUser(String username, String password) {
        if (!validateLoginUser(username, password)) {
            return;
        }

        userData.signIn(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<UserContract>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                view.showDialogForLoading();
                            }

                            @Override
                            public void onNext(UserContract value) {
                                view.notifySuccessful();
                                view.showListNotesActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.setErrorButton();
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }

    @Override
    public void onCreateAccountClicked() {
        this.view.showRegisterActivty();
    }

    @Override
    public boolean validateLoginUser(String username, String password) {
        if (username.length() > 3 && password.length() > 3) {
            return true;
        }
        else {
            return false;
        }
    }
}
