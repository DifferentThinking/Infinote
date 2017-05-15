package com.infinote.differentthinking.infinote.data.remote;

import android.content.Context;
import android.util.Log;

import com.infinote.differentthinking.infinote.config.ApiConstants;
import com.infinote.differentthinking.infinote.data.remote.base.NoteDataContract;
import com.infinote.differentthinking.infinote.models.Note;
import com.infinote.differentthinking.infinote.models.base.HttpResponseContract;
import com.infinote.differentthinking.infinote.utils.GsonParser;
import com.infinote.differentthinking.infinote.utils.OkHttpRequester;
import com.infinote.differentthinking.infinote.data.local.UserSession;

import java.lang.reflect.Type;
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
    public Observable<Boolean> saveNote(String encodedPicture, String title) {
        Map<String, String> noteCredentials = new HashMap<>();
        noteCredentials.put("username", userSession.getUsername());
        noteCredentials.put("picture", encodedPicture);
        noteCredentials.put("title", title);

        return httpRequester
                .post(apiConstants.imageUrl(userSession.getUsername()), noteCredentials)
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
        return httpRequester.get(apiConstants.imageUrl(userSession.getUsername()))
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
}
