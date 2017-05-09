package com.infinote.differentthinking.infinote.models;

import com.infinote.differentthinking.infinote.models.base.NoteContract;

public class Note implements NoteContract {
    private String id;
    private String picture;
    private String username;
    private String title;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPicture() {
        return this.picture;
    }

    @Override
    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
