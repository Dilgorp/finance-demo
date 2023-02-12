package ru.dilgorp.finance.kafka.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public abstract class BaseControllerTest extends BaseTest {
    @Autowired
    protected MockMvc mockMvc;
}