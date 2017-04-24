package com.infinote.differentthinking.infinote.utils.base;

public interface UserSessionContract {
    String getUsername();

    void setUsername(String username);

    String getId();

    void setId(String id);

    boolean isUserLoggedIn();

    void clearSession();
}
