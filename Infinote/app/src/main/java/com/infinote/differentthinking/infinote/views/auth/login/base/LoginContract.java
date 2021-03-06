package com.infinote.differentthinking.infinote.views.auth.login.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface LoginContract {

    interface View extends BaseView<LoginContract.Presenter> {
        void showListNotesActivity();

        void showDialogForLoading();

        void notifySuccessful();

        void notifyError(String errorMessage);

        void showRegisterActivty();

        void setErrorButton();
    }

    interface Presenter {
        void loginUser(String email, String password);

        boolean validateLoginUser(String username, String password);

        void onCreateAccountClicked();
    }
}