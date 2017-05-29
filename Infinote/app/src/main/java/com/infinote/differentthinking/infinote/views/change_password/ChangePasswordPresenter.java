package com.infinote.differentthinking.infinote.views.change_password;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.views.change_password.base.ChangePasswordContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {
    ChangePasswordContract.View view;
    private UserData userData;

    public ChangePasswordPresenter(ChangePasswordContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

    @Override
    public void changePasswordForUser(String password) {
        this.userData.updatePasswordForUser(password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Boolean user) {
                                view.notifySuccessful("Successfully changed password");
                                view.showListNotesActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }
}