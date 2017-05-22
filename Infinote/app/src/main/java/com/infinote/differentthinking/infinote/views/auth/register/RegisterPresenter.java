package com.infinote.differentthinking.infinote.views.auth.register;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.models.base.UserContract;
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
    public void registerUser(String username, String email, String firstname, String lastname, String password) {

        if (!validateEmail(email)) {
            return;
        }

        if (!validateUsernameAndPassword(username, password)) {
            return;
        }

        userData.signUp(username, email, firstname, lastname, password)
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
                        view.notifyError("Error ocurred when registering. Please try again later.");
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
        this.view.showLoginActivity();
    }

    private boolean validateEmail(String email) {
        if (email.contains("@") && email.length() > 4) {
            return true;

        }
        else {
            view.notifyError("Invalid email address.");
            return false;
        }

    }

    private boolean validateUsernameAndPassword(String username, String password) {
        if (username.length() >= 4 || password.length() >= 4) {
            return true;
        }
        else {
            view.notifyError("Username and password must be longer than 3 symbols");
            return false;
        }
    }
}
