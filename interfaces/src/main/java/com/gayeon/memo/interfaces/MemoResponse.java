package com.gayeon.memo.interfaces;

public class MemoResponse {

    private final Long id;
    private final String text;

    public MemoResponse(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
