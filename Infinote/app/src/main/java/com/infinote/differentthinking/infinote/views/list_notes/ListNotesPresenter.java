package com.infinote.differentthinking.infinote.views.list_notes;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.infinote.differentthinking.infinote.data.remote.NoteData;
import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.data.remote.base.NoteDataContract;
import com.infinote.differentthinking.infinote.data.remote.base.UserDataContract;
import com.infinote.differentthinking.infinote.models.Note;
import com.infinote.differentthinking.infinote.models.base.NoteContract;
import com.infinote.differentthinking.infinote.views.list_notes.base.ListNotesContract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListNotesPresenter implements ListNotesContract.Presenter {
    private UserDataContract userData;
    private NoteDataContract noteData;
    private ListNotesContract.View view;

    public ListNotesPresenter(ListNotesContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
        noteData = new NoteData(context);
    }

    public boolean isUserLoggedIn() {
        return this.userData.isLoggedIn();
    }

    public void getNotesForCurrentUser() {
        ArrayList<NoteContract> localNotes = noteData.getNotesLocally();

        for (NoteContract note: localNotes) {
            this.saveNoteFromLocalStorage(note.getPicture(), note.getTitle());
        }

        noteData.clearLocalNotes();

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

    public void deleteNoteById(String id) {
        this.noteData.deleteNoteById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.showDialogForDeleteingNote();
                    }

                    @Override
                    public void onNext(Boolean result) {
                        view.notifySuccessful();
                        view.dismissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.notifyError("Error deleting note!");
                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {

                        view.dismissDialog();
                    }
                });
    }

    public void getNotesLocally() {
        List<NoteContract> notesForAdapter = this.noteData.getNotesLocally();
        view.setupNotesAdapter(notesForAdapter);
        view.hideLoadingPanel();
    }

    @Override
    public void saveNoteFromLocalStorage(String encodedPicture, String title) {
        this.noteData.saveNote(encodedPicture, title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean value) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.notifyError("Error saving.");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
