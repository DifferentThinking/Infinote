package com.infinote.differentthinking.infinote.views.profile;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.views.profile.base.ProfileContract;

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
}
