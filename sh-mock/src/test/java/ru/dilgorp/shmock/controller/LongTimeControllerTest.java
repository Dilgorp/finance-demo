package ru.dilgorp.shmock.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.dilgorp.shmock.base.BaseControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LongTimeControllerTest extends BaseControllerTest {


    @Test
    public void getLongTime_happyPath() throws Exception {
        var result = mockMvc.perform(get("/long_time")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("1000", result.getResponse().getContentAsString());
    }

}