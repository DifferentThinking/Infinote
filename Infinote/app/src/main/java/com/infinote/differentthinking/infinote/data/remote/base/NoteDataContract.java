package com.infinote.differentthinking.infinote.data.remote.base;

import com.infinote.differentthinking.infinote.models.Note;

import java.util.List;

import io.reactivex.Observable;

public interface NoteDataContract {
    Observable<Boolean> saveNote(String encodedPicture, String title);

    Observable<List<Note>> getAllNotesForCurrentUser();
}
