package com.infinote.differentthinking.infinote.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.infinote.differentthinking.infinote.data.local.base.UserSessionContract;
import com.infinote.differentthinking.infinote.models.Note;
import com.infinote.differentthinking.infinote.models.base.NoteContract;
import com.infinote.differentthinking.infinote.utils.ObjectSerializer;

import java.io.IOException;
import java.util.ArrayList;

public class UserSession implements UserSessionContract {
    private final SharedPreferences sharedPreferences;

    public UserSession(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getUsername() {
        return sharedPreferences.getString("username", null);
    }

    @Override
    public void setUsername(String username) {
        sharedPreferences.edit().putString("username", username).apply();
    }

    @Override
    public String getId() {
        return sharedPreferences.getString("id", null);
    }

    @Override
    public void setId(String id) {
        sharedPreferences.edit().putString("id", id).apply();
    }

    @Override
    public void addNote(String noteAsString, String title, String dateAsString) throws IOException, ClassNotFoundException {
        ArrayList<NoteContract> notes;
        if (sharedPreferences.getString("notes", null) != null) {
            notes = (ArrayList<NoteContract>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", null));
        }
        else {
            notes = new ArrayList<>();
        }
        NoteContract note = new Note();
        note.setPicture(noteAsString);
        note.setTitle(title);
        note.setDate(dateAsString);

        notes.add(note);
        sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();
    }

    @Override
    public ArrayList<NoteContract> getNotes() throws IOException, ClassNotFoundException {
        if (sharedPreferences.getString("notes", null) == null) {
            return new ArrayList<NoteContract>();
        }

        else {
            return (ArrayList<NoteContract>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", null));
        }
    }

    public void clearNotes() {
        sharedPreferences.edit().putString("notes", null).apply();
    }

    public boolean isUserLoggedIn() {
        String username = getUsername();
        return username != null;
    }

    public void clearSession() {
        setUsername(null);
        setId(null);

    }
}
