package com.infinote.differentthinking.infinote.views.note.base;

import com.infinote.differentthinking.infinote.base.BaseView;
import com.infinote.differentthinking.infinote.views.note.NotePresenter;

public interface NoteContract {
    interface View extends BaseView<NoteContract.Presenter> {
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
