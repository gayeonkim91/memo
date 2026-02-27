package com.gayeon.memo.interfaces;

public class MemoCreateRequest {

    private String text;

    public MemoCreateRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
