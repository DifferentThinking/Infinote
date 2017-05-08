package com.infinote.differentthinking.infinote.views.note.base;

public interface NoteContract {
    interface View {
        void showListNotesActivity();

        void showDialogForLoading();

        void dismissDialog();

        void notifySuccessful();

        void notifyError(String errorMessage);
    }

    interface Presenter {
        void saveNote(String encodedPicture);
    }
}
