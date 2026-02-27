package com.gayeon.memo.domain;

import org.springframework.stereotype.Service;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public Memo create(String text) {
        return memoRepository.save(new Memo(text));
    }
}
