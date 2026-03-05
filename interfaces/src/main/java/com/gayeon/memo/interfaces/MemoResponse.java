package com.gayeon.memo.interfaces;

public class MemoResponse {

    private Long id;
    private String text;

    public MemoResponse() {
    }

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

    public void setText(String text) {
        this.text = text;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
