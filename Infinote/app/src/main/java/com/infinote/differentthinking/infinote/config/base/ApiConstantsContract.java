package com.infinote.differentthinking.infinote.config.base;

public interface ApiConstantsContract {
    String signInUrl();

    String signUpUrl();

    String notesForCurrentUserUrl(String username);

    String deleteNoteUrl();

    String singleUserUrl(String username);

    String updateNoteById(String id);

    String profilePictureUrl(String username);

    int responseSuccessCode();

    int responseErrorCode();

}
