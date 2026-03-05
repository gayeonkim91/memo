package com.gayeon.memo.interfaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gayeon.memo.domain.Memo;
import com.gayeon.memo.domain.MemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class MemoControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemoRepository memoRepository;

    private MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void createMemoReturns201() throws Exception {
        mockMvc().perform(post("/memo").contentType(MediaType.APPLICATION_JSON).content("{\"text\":\"hello\"}")).andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.text").value("hello"));
    }

    @Test
    void createMemoReturns400WhenTextIsBlank() throws Exception {
        mockMvc().perform(post("/memo").contentType(MediaType.APPLICATION_JSON).content("{\"text\":\"   \"}")).andExpect(status().isBadRequest());
    }

    @Test
    void getMemoReturns200WhenIdExists() throws Exception {
        Memo savedMemo = memoRepository.save(new Memo("saved memo"));
        mockMvc().perform(get("/memo/" + savedMemo.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(savedMemo.getId())).andExpect(jsonPath("$.text").value("saved memo"));
    }

    @Test
    void getMemoReturns404WhenIdDoesNotExist() throws Exception {
        mockMvc().perform(get("/memo/999999")).andExpect(status().isNotFound());
    }

    @Test
    void getMemosReturns200WithTwoMemosSortedByIdDesc() throws Exception {
        memoRepository.deleteAll();
        Memo firstMemo = memoRepository.save(new Memo("first"));
        Memo secondMemo = memoRepository.save(new Memo("second"));
        mockMvc().perform(get("/memos")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(secondMemo.getId())).andExpect(jsonPath("$[0].text").value("second")).andExpect(jsonPath("$[1].id").value(firstMemo.getId())).andExpect(jsonPath("$[1].text").value("first"));
    }

    @Test
    void getMemosReturns200WithEmptyListWhenNoMemoExists() throws Exception {
        memoRepository.deleteAll();
        mockMvc().perform(get("/memos")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void updateMemoReturns200AndUpdatesTextWhenIdExists() throws Exception {
        Memo savedMemo = memoRepository.save(new Memo("before"));

        mockMvc().perform(put("/memo/" + savedMemo.getId()).contentType(MediaType.APPLICATION_JSON).content("{\"text\":\"after\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(savedMemo.getId())).andExpect(jsonPath("$.text").value("after"));

        Memo updatedMemo = memoRepository.findById(savedMemo.getId()).orElseThrow();
        assertThat(updatedMemo.getText()).isEqualTo("after");
    }

    @Test
    void updateMemoReturns404WhenIdDoesNotExist() throws Exception {
        mockMvc().perform(put("/memo/999999").contentType(MediaType.APPLICATION_JSON).content("{\"text\":\"after\"}")).andExpect(status().isNotFound());
    }

    @Test
    void updateMemoReturns400WhenTextIsBlank() throws Exception {
        Memo savedMemo = memoRepository.save(new Memo("before"));

        mockMvc().perform(put("/memo/" + savedMemo.getId()).contentType(MediaType.APPLICATION_JSON).content("{\"text\":\"   \"}")).andExpect(status().isBadRequest());
    }

    @Test
    void deleteMemoReturns204AndDeletesMemoWhenIdExists() throws Exception {
        Memo savedMemo = memoRepository.save(new Memo("to delete"));

        mockMvc().perform(delete("/memo/" + savedMemo.getId())).andExpect(status().isNoContent());

        assertThat(memoRepository.findById(savedMemo.getId())).isEmpty();
    }

    @Test
    void deleteMemoReturns404WhenIdDoesNotExist() throws Exception {
        Memo savedMemo = memoRepository.save(new Memo("existing memo"));
        Long missingId = savedMemo.getId() + 999999L;

        mockMvc().perform(delete("/memo/" + missingId)).andExpect(status().isNotFound());
    }
}
