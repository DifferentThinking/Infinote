package com.infinote.differentthinking.infinote.views.list_notes.base;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import com.infinote.differentthinking.infinote.base.BaseView;
import com.infinote.differentthinking.infinote.models.base.NoteContract;

import java.util.List;
import java.util.Set;

public interface ListNotesContract {
    interface View extends BaseView<ListNotesContract.Presenter> {
        void showProfileActivity();

        void showLoginActivity();

        void showNewNoteActivity();

        void setupNotesAdapter(List<? extends NoteContract> notes);

        void notifyError(String errorMessage);

        void notifySuccessful();

        void hideLoadingPanel();

        void showNewNoteActivityWithImage(byte[] encodedImage);

        void showDialogForDeleteingNote();

        void dismissDialog();

        ArrayAdapter<NoteContract> getNoteAdapter();
    }

    interface Presenter {
        boolean isUserLoggedIn();

        void getNotesForCurrentUser();

        void deleteNoteById(String id);

        void getNotesLocally();

        void saveNoteFromLocalStorage(String encodedPicture, String title);
    }
}
