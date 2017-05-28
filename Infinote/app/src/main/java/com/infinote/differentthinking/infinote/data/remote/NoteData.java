package com.infinote.differentthinking.infinote.data.remote;

import android.content.Context;
import android.util.Log;

import com.infinote.differentthinking.infinote.config.ApiConstants;
import com.infinote.differentthinking.infinote.data.remote.base.NoteDataContract;
import com.infinote.differentthinking.infinote.models.Note;
import com.infinote.differentthinking.infinote.models.base.HttpResponseContract;
import com.infinote.differentthinking.infinote.models.base.NoteContract;
import com.infinote.differentthinking.infinote.utils.GsonParser;
import com.infinote.differentthinking.infinote.utils.OkHttpRequester;
import com.infinote.differentthinking.infinote.data.local.UserSession;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class NoteData implements NoteDataContract {
    private final OkHttpRequester httpRequester;
    private final ApiConstants apiConstants;
    private final GsonParser jsonParser;
    private final UserSession userSession;
    private final Type noteModelType;

    public NoteData(Context context) {
        this.jsonParser = new GsonParser();
        this.httpRequester = new OkHttpRequester();
        this.apiConstants = new ApiConstants();
        this.userSession = new UserSession(context);
        this.noteModelType = Note.class;
    }

    @Override
    public Observable<Boolean> saveNote(String encodedPicture, String title, String dateAsString) {
        Map<String, String> noteCredentials = new HashMap<>();
        noteCredentials.put("username", userSession.getUsername());
        noteCredentials.put("picture", encodedPicture);
        noteCredentials.put("title", title);
        noteCredentials.put("date", dateAsString);

        return httpRequester
            .post(apiConstants.notesForCurrentUserUrl(userSession.getUsername()), noteCredentials)
            .map(new Function<HttpResponseContract, Boolean>() {
                @Override
                public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                    throw new Error(iHttpResponse.getMessage());
                }
                String responseBody = iHttpResponse.getBody().toString();
                if (responseBody.contains("OK")) {
                   return true;
                }
                else {
                    return false;
                }
                }
            });
    }

    public Observable<List<Note>> getAllNotesForCurrentUser() {
        return httpRequester
                .get(apiConstants.notesForCurrentUserUrl(userSession.getUsername()))
                .map(new Function<HttpResponseContract, List<Note>>() {
                    @Override
                    public List<Note> apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }
                        String responseBody = iHttpResponse.getBody().toString();
                        String noteJsonArray = jsonParser.getDirectMember(responseBody, "result");
                        return jsonParser.getDirectArray(noteJsonArray, "notes", Note.class);
                    }
                });
    }

    public Observable<Boolean> deleteNoteById(String id) {
        Map<String, String> noteCredentials = new HashMap<>();
        noteCredentials.put("id", id);

        return httpRequester
                .post(apiConstants.deleteNoteUrl(), noteCredentials)
                .map(new Function<HttpResponseContract, Boolean>() {
                    @Override
                    public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }
                        String responseBody = iHttpResponse.getBody().toString();
                        if (responseBody.contains("OK")) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });
    }

    @Override
    public void saveNoteLocally(String encodedPicture, String title, String dateAsString) {
        try{
            this.userSession.addNote(encodedPicture, title, dateAsString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<NoteContract> getNotesLocally() {
        try{
            return this.userSession.getNotes();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void clearLocalNotes() {
        this.userSession.clearNotes();
    }

    @Override
    public Observable<Boolean> updateNote(String id, String encodedPicture, String title, String dateAsString) {
        Map<String, String> noteCredentials = new HashMap<>();
        noteCredentials.put("picture", encodedPicture);
        noteCredentials.put("title", title);
        noteCredentials.put("username", userSession.getUsername());
        noteCredentials.put("date", dateAsString);

        return httpRequester
                .post(apiConstants.updateNoteById(id), noteCredentials)
                .map(new Function<HttpResponseContract, Boolean>() {
                    @Override
                    public Boolean apply(HttpResponseContract iHttpResponse) throws Exception {
                        if (iHttpResponse.getCode() == apiConstants.responseErrorCode()) {
                            throw new Error(iHttpResponse.getMessage());
                        }
                        String responseBody = iHttpResponse.getBody().toString();
                        if (responseBody.contains("OK")) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });
    }
}
