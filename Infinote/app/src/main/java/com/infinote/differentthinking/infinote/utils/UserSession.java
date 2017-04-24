package com.infinote.differentthinking.infinote.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.infinote.differentthinking.infinote.utils.base.UserSessionContract;

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

    public boolean isUserLoggedIn() {
        String username = getUsername();
        return username != null;
    }

    public void clearSession() {
        setUsername(null);
        setId(null);
    }
}
