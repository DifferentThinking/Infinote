package com.infinote.differentthinking.infinote.views.note;

import android.content.Context;

import com.infinote.differentthinking.infinote.data.remote.UserData;
import com.infinote.differentthinking.infinote.models.base.UserContract;
import com.infinote.differentthinking.infinote.views.note.base.NoteContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotePresenter implements NoteContract.Presenter {
    private UserData userData;
    NoteContract.View view;

    public NotePresenter(NoteContract.View view, Context context) {
        this.view = view;
        userData = new UserData(context);
    }

    @Override
    public void saveNote() {
        this.userData.saveNote()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<UserContract>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        view.showDialogForLoading();
                    }

                    @Override
                    public void onNext(UserContract value) {
//                        view.notifySuccessful();
//                        view.showListNotesActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        view.notifyError("Invalid useranme or password");
//                        view.dismissDialog();
                    }

                    @Override
                    public void onComplete() {
//                        view.dismissDialog();
                    }
                });
    }
}
