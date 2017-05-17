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
        String username = sharedPreferences.getString("username", null);
        return username;
    }

    @Override
    public void setUsername(String username) {
        sharedPreferences.edit().putString("username", username).commit();
    }

    @Override
    public String getId() {
        String id = sharedPreferences.getString("id", null);
        return id;
    }

    @Override
    public void setId(String id) {
        sharedPreferences.edit().putString("id", id).commit();
    }

    @Override
    public void addNote(String noteAsString, String title) throws IOException, ClassNotFoundException {
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

        notes.add(note);
        sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).commit();
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
        sharedPreferences.edit().putString("notes", null).commit();
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
