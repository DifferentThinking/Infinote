package com.infinote.differentthinking.infinote.views.single_note.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface SingleNoteContract {
    interface View extends BaseView<SingleNoteContract.Presenter> {
        void showListNotesActivity();

        void showDialogForLoading();

        void dismissDialog();

        void notifySuccessful();

        void notifyError(String errorMessage);
    }

    interface Presenter {
        void saveNote(String encodedPicture, String title);

        boolean isUserLoggedIn();

        void saveNoteLocally(String encodedPicture, String title);
    }
}
