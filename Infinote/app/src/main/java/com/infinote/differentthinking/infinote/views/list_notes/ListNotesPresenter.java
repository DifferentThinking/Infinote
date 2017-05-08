package com.infinote.differentthinking.infinote.views.list_notes;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.views.list_notes.base.ListNotesContract;

public class ListNotesPresenter implements ListNotesContract.Presenter {
    private UserData userData;
    ListNotesContract.View view;

    public ListNotesPresenter(ListNotesContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

    public boolean isUserLoggedIn() {
        return this.userData.isLoggedIn();
    }
}
