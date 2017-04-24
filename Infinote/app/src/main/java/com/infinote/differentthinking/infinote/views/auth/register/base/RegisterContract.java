package com.infinote.differentthinking.infinote.views.auth.register.base;

public interface RegisterContract {

    interface View {
        void showDialogForLoading();

        void dismissDialog();

        void showListNotesActivity();
    }

    interface Presenter{
        void registerUser(String email, String password);

        void onHasAccountClicked();
    }
}