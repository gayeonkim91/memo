package com.gayeon.memo.domain;

import java.util.List;
import java.util.Optional;
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

    public Optional<Memo> getById(Long id) {
        return memoRepository.findById(id);
    }

    public List<Memo> getAll() {
        return memoRepository.findAllByOrderByIdDesc();
    }

    public Optional<Memo> update(Long id, String text) {
        return memoRepository.findById(id).map(memo -> {
            memo.updateText(text);
            return memoRepository.save(memo);
        });
    }

    public boolean delete(Long id) {
        return memoRepository.findById(id).map(memo -> {
            memoRepository.delete(memo);
            return true;
        }).orElse(false);
    }
}
