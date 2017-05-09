package com.infinote.differentthinking.infinote.views.list_notes.base;

import com.infinote.differentthinking.infinote.base.BaseView;
import com.infinote.differentthinking.infinote.models.base.NoteContract;

import java.util.List;

public interface ListNotesContract {
    interface View extends BaseView<ListNotesContract.Presenter> {
        void showProfileActivity();

        void showLoginActivity();

        void showNewNoteActivity();

        void setupNotesAdapter(List<? extends NoteContract> notes);

        void notifyError(String errorMessage);

        void notifySuccessful();

        void hideLoadingPanel();
    }

    interface Presenter {
        boolean isUserLoggedIn();

        void getNotesForCurrentUser();
    }
}
