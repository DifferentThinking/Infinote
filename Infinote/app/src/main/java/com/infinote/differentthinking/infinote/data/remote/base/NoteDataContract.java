package com.infinote.differentthinking.infinote.data.remote.base;

import com.infinote.differentthinking.infinote.models.Note;
import com.infinote.differentthinking.infinote.models.base.NoteContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

public interface NoteDataContract {
    Observable<Boolean> saveNote(String encodedPicture, String title, String dateAsString);

    Observable<List<Note>> getAllNotesForCurrentUser();

    Observable<Boolean> deleteNoteById(String id);

    Observable<Boolean> updateNote(String id, String encodedPicture, String title, String dateAsString);

    void saveNoteLocally(String encodedPicture, String title, String dateAsString);

    ArrayList<NoteContract> getNotesLocally();

    void clearLocalNotes();
}
