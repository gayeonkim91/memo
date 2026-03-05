package com.gayeon.memo.interfaces;

import com.gayeon.memo.domain.Memo;
import com.gayeon.memo.domain.MemoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoController {

    private static final int MAX_MEMO_TEXT_LENGTH = 2000;

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/memo")
    public ResponseEntity<MemoResponse> create(@RequestBody MemoCreateRequest request) {
        String text = request.getText();
        if (isInvalidText(text)) {
            return ResponseEntity.badRequest().build();
        }

        Memo memo = memoService.create(text);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(memo));
    }

    @PutMapping("/memo/{id}")
    public ResponseEntity<MemoResponse> update(@PathVariable Long id, @RequestBody MemoCreateRequest request) {
        String text = request.getText();
        if (isInvalidText(text)) {
            return ResponseEntity.badRequest().build();
        }

        return memoService.update(id, text).map(memo -> ResponseEntity.ok(toResponse(memo))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/memo/{id}")
    public ResponseEntity<MemoResponse> getById(@PathVariable Long id) {
        return memoService.getById(id).map(memo -> ResponseEntity.ok(toResponse(memo))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/memos")
    public ResponseEntity<List<MemoResponse>> getAll() {
        List<MemoResponse> response = memoService.getAll().stream().map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/memo/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (memoService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean isInvalidText(String text) {
        return text == null || text.isBlank() || text.length() > MAX_MEMO_TEXT_LENGTH;
    }

    private MemoResponse toResponse(Memo memo) {
        return new MemoResponse(memo.getId(), memo.getText());
    }
}
