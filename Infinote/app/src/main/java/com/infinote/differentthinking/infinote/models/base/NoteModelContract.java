package com.infinote.differentthinking.infinote.models.base;

public interface NoteModelContract {
    String getId();

    void setId(String id);

    byte[] getBytes();

    void setBytes(byte[] bytes);
}
