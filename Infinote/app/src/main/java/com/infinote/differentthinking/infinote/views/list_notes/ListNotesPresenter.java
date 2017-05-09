package com.infinote.differentthinking.infinote.views.list_notes;

import android.content.Context;
import android.util.Log;

import com.infinote.differentthinking.infinote.data.remote.NoteData;
import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.models.Note;
import com.infinote.differentthinking.infinote.views.list_notes.base.ListNotesContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListNotesPresenter implements ListNotesContract.Presenter {
    private UserData userData;
    private NoteData noteData;
    ListNotesContract.View view;

    public ListNotesPresenter(ListNotesContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
        noteData = new NoteData(context);
    }

    public boolean isUserLoggedIn() {
        return this.userData.isLoggedIn();
    }

    public void getNotesForCurrentUser() {
        this.noteData.getAllNotesForCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<Note>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<Note> notes) {
                                view.setupNotesAdapter(notes);
                                view.notifySuccessful();
                                view.hideLoadingPanel();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error loading notes!");
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
    }
}
