package com.infinote.differentthinking.infinote.views.single_note;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.NoteData;
import com.infinote.differentthinking.infinote.views.single_note.base.SingleNoteContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SingleNotePresenter implements SingleNoteContract.Presenter {
    private NoteData noteData;
    SingleNoteContract.View view;

    public SingleNotePresenter(SingleNoteContract.View view, Context context) {
        this.view = view;
        noteData = new NoteData(context);
    }

    @Override
    public void saveNote(String encodedPicture) {
        this.noteData.saveNote(encodedPicture)
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
                        view.notifyError("Error saving");
                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        view.dismissDialog();
                    }
                });
    }
}
