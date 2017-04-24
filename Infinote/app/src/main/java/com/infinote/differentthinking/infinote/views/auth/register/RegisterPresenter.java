package com.infinote.differentthinking.infinote.views.auth.register;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.UserData;
import com.infinote.differentthinking.infinote.models.IUser;
import com.infinote.differentthinking.infinote.views.auth.register.base.RegisterContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter implements RegisterContract.Presenter {
    private UserData userData;
    private RegisterContract.View view;

    public RegisterPresenter(RegisterContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

    @Override
    public void registerUser(String email, String password) {
        userData.signUp(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<IUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.showDialogForLoading();
                    }

                    @Override
                    public void onNext(IUser value) {
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        view.dismissDialog();
                    }
                });
    }

    @Override
    public void onHasAccountClicked() {
        this.view.showListNotesActivity();
    }
}
