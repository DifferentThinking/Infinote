package com.infinote.differentthinking.infinote.models.base;

public interface UserContract {
    String getId();

    void setId(String id);

    String getUsername();

    void setUsername(String username);

    String getLastname();

    void setLastname(String lastname);

    String getFirstname();

    void setFirstname(String firstname);

    String getEmail();

    void setEmail(String email);
}
