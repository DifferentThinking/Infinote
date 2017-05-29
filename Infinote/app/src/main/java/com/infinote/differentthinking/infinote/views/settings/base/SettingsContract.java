package com.infinote.differentthinking.infinote.views.settings.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface SettingsContract {
    interface View extends BaseView<Presenter> {
        void showDialogForLoading();

        void notifySuccessful(String message);

        void notifyError(String errorMessage);

        void showProfileActivity();

        void dismissDialog();
    }

    interface Presenter {
        void saveProfilePicture(String encodedPicture);
    }
}