package com.infinote.differentthinking.infinote.models;

import com.infinote.differentthinking.infinote.models.base.UserContract;

public class User implements UserContract {
    private String id;
    private String username;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
