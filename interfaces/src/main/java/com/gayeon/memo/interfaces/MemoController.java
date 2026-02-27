package com.gayeon.memo.interfaces;

import com.gayeon.memo.domain.Memo;
import com.gayeon.memo.domain.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
