package com.infinote.differentthinking.infinote.views.drawing.base;

import com.infinote.differentthinking.infinote.base.BaseView;

public interface DrawingContract {
    interface View extends BaseView<DrawingContract.Presenter> {
        void showListNotesActivity();

        void showDialogForLoading();

        void dismissDialog();

        void notifySuccessful();

        void notifyError(String errorMessage);

        void setCanvasText(String text);
    }

    interface Presenter {
        void saveNote(String encodedPicture, String title, String dateAsString);

        boolean isUserLoggedIn();

        void saveNoteLocally(String encodedPicture, String title, String dateAsString);

        void updateNote(String id, String encodedPicture, String title, String dateAsString);
    }
}
