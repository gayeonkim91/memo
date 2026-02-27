package com.gayeon.memo.interfaces;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void createMemoReturns201() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockMvc.perform(post("/memo").contentType(MediaType.APPLICATION_JSON).content("{\"text\":\"hello\"}")).andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.text").value("hello"));
    }

    @Test
    void createMemoReturns400WhenTextIsBlank() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockMvc.perform(post("/memo").contentType(MediaType.APPLICATION_JSON).content("{\"text\":\"   \"}")).andExpect(status().isBadRequest());
    }

    @Test
    void getMemoReturns200WhenIdExists() throws Exception {
        Memo savedMemo = memoRepository.save(new Memo("saved memo"));
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockMvc.perform(get("/memo/" + savedMemo.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(savedMemo.getId())).andExpect(jsonPath("$.text").value("saved memo"));
    }

    @Test
    void getMemoReturns404WhenIdDoesNotExist() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockMvc.perform(get("/memo/999999")).andExpect(status().isNotFound());
    }
}
