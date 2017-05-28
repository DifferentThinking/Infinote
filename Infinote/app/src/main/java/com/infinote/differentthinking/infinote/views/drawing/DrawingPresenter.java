package com.infinote.differentthinking.infinote.views.drawing;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.NoteData;
import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.views.drawing.base.DrawingContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DrawingPresenter implements DrawingContract.Presenter {
    private NoteData noteData;
    private UserData userData;
    DrawingContract.View view;

    public DrawingPresenter(DrawingContract.View view, Context context) {
        this.view = view;
        noteData = new NoteData(context);
        userData = new UserData(context);
    }

    @Override
    public void saveNote(String encodedPicture, String title, String dateAsString) {
        this.noteData.saveNote(encodedPicture, title, dateAsString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.showDialogForLoading();
                    }

                    @Override
                    public void onNext(Boolean value) {
                        view.notifySuccessful();
                        view.showListNotesActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.notifyError("Error saving.");
                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        view.dismissDialog();
                    }
                });
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.userData.isLoggedIn();
    }

    @Override
    public void saveNoteLocally(String encodedPicture, String title, String dateAsString) {
        this.noteData.saveNoteLocally(encodedPicture, title, dateAsString);
        view.notifySuccessful();
        view.showListNotesActivity();
    }

    @Override
    public void updateNote(String id, String encodedPicture, String title, String dateAsString) {
        this.noteData.updateNote(id, encodedPicture, title, dateAsString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                view.showDialogForLoading();
                            }

                            @Override
                            public void onNext(Boolean value) {
                                view.notifySuccessful();
                                view.showListNotesActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.notifyError("Error saving.");
                                view.dismissDialog();
                            }

                            @Override
                            public void onComplete() {
                                view.dismissDialog();
                            }
                        });
    }
}
