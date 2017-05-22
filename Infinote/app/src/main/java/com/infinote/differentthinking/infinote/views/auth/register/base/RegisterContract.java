package com.infinote.differentthinking.infinote.views.auth.register.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface RegisterContract {

    interface View extends BaseView<RegisterContract.Presenter> {
        void showDialogForLoading();

        void dismissDialog();

        void showListNotesActivity();

        void showLoginActivity();

        void notifySuccessful();

        void notifyError(String errorMessage);

        void setProgressButttonTo100();

        void setProgressButttonTo50();

        void setProgressButttonTo0();
    }

    interface Presenter{
        void registerUser(String username, String email, String firstname, String lastname, String password);

        void onHasAccountClicked();
    }
}