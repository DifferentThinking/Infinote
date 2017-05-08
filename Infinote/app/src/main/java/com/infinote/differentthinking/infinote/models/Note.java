package com.infinote.differentthinking.infinote.models;

import com.infinote.differentthinking.infinote.views.note.base.NoteContract;

public class Note implements NoteContract {
    private String id;
    private byte[] bytes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
