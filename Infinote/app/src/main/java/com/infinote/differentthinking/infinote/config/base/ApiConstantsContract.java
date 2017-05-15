package com.infinote.differentthinking.infinote.config.base;

public interface ApiConstantsContract {
    String signInUrl();

    String signUpUrl();

    String imageUrl(String username);

    String deleteNoteUrl();

    int responseSuccessCode();

    int responseErrorCode();

}
