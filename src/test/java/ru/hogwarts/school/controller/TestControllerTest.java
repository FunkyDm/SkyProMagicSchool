package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.service.impl.TestServiceImpl;

import static org.mockito.ArgumentMatchers.same;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @MockBean
    private TestServiceImpl testService;

    @Autowired
    private MockMvc mvc;

    @Test
    void getTestSum() throws Exception{
        long testVariable1 = 123L;
        long testVariable2 = 5000000000L;

        when(testService.getTestSum()).thenReturn(List.of(testVariable1,testVariable2));

        mvc.perform(get("/get/test/sum"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(123L))
                .andExpect(jsonPath("$[1]").value(5000000000L));
    }
}