package com.infinote.differentthinking.infinote.views.list_notes.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface ListNotesContract {
    interface View extends BaseView<ListNotesContract.Presenter> {
        void showProfileActivity();

        void showLoginActivity();

        void showNewNoteActivity();
    }

    interface Presenter {
        boolean isUserLoggedIn();
    }
}
