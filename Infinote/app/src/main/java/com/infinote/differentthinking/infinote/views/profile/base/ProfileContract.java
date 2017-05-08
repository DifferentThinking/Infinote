package com.infinote.differentthinking.infinote.views.profile.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface ProfileContract {
    interface View extends BaseView<ProfileContract.Presenter> {
        void notifyLogout();

        void showListNotesActivity();
    }

    interface Presenter {
        void logoutUser();

        boolean isUserLoggedIn();
    }
}

