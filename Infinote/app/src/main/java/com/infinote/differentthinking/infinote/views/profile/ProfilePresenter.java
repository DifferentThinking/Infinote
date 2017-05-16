package com.infinote.differentthinking.infinote.views.profile;

import android.content.Context;
import android.util.Log;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.models.base.UserContract;
import com.infinote.differentthinking.infinote.views.profile.base.ProfileContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfilePresenter implements ProfileContract.Presenter {
    ProfileContract.View view;
    private UserData userData;

    public ProfilePresenter(ProfileContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

    @Override
    public void logoutUser() {
        this.userData.logoutUser();
        this.view.notifyLogout();
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.userData.isLoggedIn();
    }

    @Override
    public void getInfoForCurrentUser() {
        this.userData.getInfoForCurrentUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<UserContract>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.showDialogForLoading();
                    }

                    @Override
                    public void onNext(UserContract user) {
                        view.setupProfile(user);
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        view.dismissDialog();
                    }
                });
    }
}
