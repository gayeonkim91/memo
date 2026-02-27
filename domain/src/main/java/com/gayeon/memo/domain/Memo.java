package com.gayeon.memo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "memos")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    protected Memo() {
    }

    public Memo(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
