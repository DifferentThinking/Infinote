package com.infinote.differentthinking.infinote.models;

import com.infinote.differentthinking.infinote.models.base.NoteContract;

public class Note implements NoteContract {
    private String id;
    private String picture;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
