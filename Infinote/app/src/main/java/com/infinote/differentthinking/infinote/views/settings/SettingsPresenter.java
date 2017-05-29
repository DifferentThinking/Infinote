package com.infinote.differentthinking.infinote.views.settings;

import android.content.Context;
import android.util.Log;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.views.settings.base.SettingsContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenter implements SettingsContract.Presenter {
    SettingsContract.View view;
    private UserData userData;

    public SettingsPresenter(SettingsContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

    @Override
    public void saveProfilePicture(String encodedPicture) {
        this.userData.savePictureForUser(encodedPicture)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.showDialogForLoading();
                    }

                    @Override
                    public void onNext(Boolean value) {
                        view.notifySuccessful("Profile updated successfully");
                        view.showProfileActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.notifyError("Error saving.");
                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                            view.dismissDialog();
                        }
                    });
    }
}