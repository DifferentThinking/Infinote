package com.infinote.differentthinking.infinote.data.local.base;

import com.infinote.differentthinking.infinote.models.base.NoteContract;

import java.io.IOException;
import java.util.ArrayList;

public interface UserSessionContract {
    String getUsername();

    void setUsername(String username);

    String getId();

    void setId(String id);

    boolean isUserLoggedIn();

    void addNote(String noteAsString, String title, String dateAsString) throws IOException, ClassNotFoundException;

    ArrayList<NoteContract> getNotes() throws IOException, ClassNotFoundException;

    void clearNotes();

    void clearSession();
}
