package com.infinote.differentthinking.infinote.views.profile.base;

public interface ProfileContract {
    interface View {
        void notifyLogout();
    }

    interface Presenter {
        void logoutUser();

        boolean isUserLoggedIn();
    }
}

