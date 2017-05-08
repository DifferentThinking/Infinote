package com.infinote.differentthinking.infinote.views.auth.register.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface RegisterContract {

    interface View extends BaseView<RegisterContract.Presenter> {
        void showDialogForLoading();

        void dismissDialog();

        void showListNotesActivity();

        void notifySuccessful();

        void notifyError(String errorMessage);
    }

    interface Presenter{
        void registerUser(String email, String password);

        void onHasAccountClicked();
    }
}