package com.infinote.differentthinking.infinote.views.drawing.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface DrawingContract {
    interface View extends BaseView<DrawingContract.Presenter> {
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

        void updateNote(String id, String encodedPicture, String title);
    }
}
