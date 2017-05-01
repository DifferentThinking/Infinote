package com.infinote.differentthinking.infinote.views.note;

import android.content.Context;

import com.infinote.differentthinking.infinote.views.note.base.NoteContract;

public class NotePresenter implements NoteContract.Presenter {
    //private UserData userData;
    NoteContract.View view;

    public NotePresenter(NoteContract.View view, Context context) {
        this.view = view;
        //userData = new UserData(context);
    }
}
