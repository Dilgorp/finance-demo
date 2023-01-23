package ru.dilgorp.shmock.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.dilgorp.shmock.base.BaseControllerTest;
import ru.dilgorp.shmock.service.MessagingService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessagingControllerTest extends BaseControllerTest {

    @MockBean
    private MessagingService messagingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void send_happyPath() throws Exception {

        mockMvc.perform(get("/messages/send?seq=1&partitionSize=2&dealsCount=3&partitionsShuffle=4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(messagingService).send(2, 3, 1, 4);
    }

}