package com.infinote.differentthinking.infinote.data.remote.base;

import com.infinote.differentthinking.infinote.models.base.NoteModelContract;

import io.reactivex.Observable;

public interface NoteDataContract {
    Observable<Boolean> saveNote(String encodedPicture);
}
