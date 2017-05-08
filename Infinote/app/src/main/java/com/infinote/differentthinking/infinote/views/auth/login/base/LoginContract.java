package com.infinote.differentthinking.infinote.views.auth.login.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface LoginContract {

    interface View extends BaseView<LoginContract.Presenter> {
        void showListNotesActivity();

        void showDialogForLoading();

        void dismissDialog();

        void notifySuccessful();

        void notifyError(String errorMessage);

        void showRegisterActivty();
    }

    interface Presenter {
        void loginUser(String email, String password);

        void onCreateAccountClicked();

        void onFogotPasswordClicked();
    }
}