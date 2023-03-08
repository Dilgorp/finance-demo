package ru.dilgorp.finance.kafka.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dilgorp.finance.kafka.base.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShMockClientApiTest extends BaseTest {

    @Autowired
    private ShMockClientApi shMockClientApi;

    @Test
    public void longTime_happyPath() {
        var result = shMockClientApi.longTime();

        assertEquals(1000L, result);
    }
}