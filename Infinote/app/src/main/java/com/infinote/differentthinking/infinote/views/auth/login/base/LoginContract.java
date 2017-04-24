package com.infinote.differentthinking.infinote.views.auth.login.base;

public interface LoginContract {

    interface View {
        void showListNotesActivity();

        public void showDialogForLoading();

        void dismissDialog();

        void notifySuccessful();

        void notifyError(String errorMessage);

        void showRegisterActivty();
    }

    interface Presenter{
        void loginUser(String email, String password);

        void onCreateAccountClicked();

        void onFogotPasswordClicked();
    }
}