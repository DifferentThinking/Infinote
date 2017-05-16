package com.infinote.differentthinking.infinote.views.profile.base;

import com.infinote.differentthinking.infinote.base.BaseView;
import com.infinote.differentthinking.infinote.models.base.UserContract;

public interface ProfileContract {
    interface View extends BaseView<ProfileContract.Presenter> {
        void notifyLogout();

        void showListNotesActivity();

        void setupProfile(UserContract user);
    }

    interface Presenter {
        void logoutUser();

        boolean isUserLoggedIn();

        void getInfoForCurrentUser();
    }
}

