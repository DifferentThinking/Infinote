package com.infinote.differentthinking.infinote.config.base;

public interface ApiConstantsContract {
    String signInUrl();

    String signUpUrl();

    String notesForCurrentUserUrl(String username);

    String deleteNoteUrl();

    String singleUserUrl(String username);

    String updateNoteById(String id);

    int responseSuccessCode();

    int responseErrorCode();

}
