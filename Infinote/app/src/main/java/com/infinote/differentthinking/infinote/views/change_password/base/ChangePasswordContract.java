package com.infinote.differentthinking.infinote.views.change_password.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface ChangePasswordContract {
    interface View extends BaseView<Presenter> {
        void notifySuccessful(String message);

        void showListNotesActivity();
    }

    interface Presenter {
        void changePasswordForUser(String password);
    }
}
