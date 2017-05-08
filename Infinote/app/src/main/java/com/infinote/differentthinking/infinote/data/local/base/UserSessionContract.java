package com.infinote.differentthinking.infinote.data.local.base;

public interface UserSessionContract {
    String getUsername();

    void setUsername(String username);

    String getId();

    void setId(String id);

    boolean isUserLoggedIn();

    void clearSession();
}
