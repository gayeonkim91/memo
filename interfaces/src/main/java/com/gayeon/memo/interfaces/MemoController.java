package com.gayeon.memo.interfaces;

import com.gayeon.memo.domain.Memo;
import com.gayeon.memo.domain.MemoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/memo")
    public ResponseEntity<MemoResponse> create(@RequestBody MemoCreateRequest request) {
        String text = request.getText();
        if (text == null || text.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Memo memo = memoService.create(text);
        MemoResponse response = new MemoResponse(memo.getId(), memo.getText());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/memo/{id}")
    public ResponseEntity<MemoResponse> getById(@PathVariable Long id) {
        return memoService.getById(id).map(memo -> ResponseEntity.ok(new MemoResponse(memo.getId(), memo.getText()))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/memos")
    public ResponseEntity<List<MemoResponse>> getAll() {
        List<MemoResponse> response = memoService.getAll().stream().map(memo -> new MemoResponse(memo.getId(), memo.getText())).toList();
        return ResponseEntity.ok(response);
    }
}
