package ru.dilgorp.shmock.stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.message.RecalculatePriceMessage;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;
import ru.dilgorp.shmock.base.BaseStreamTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SenderTest extends BaseStreamTest {

    @Autowired
    private Sender sender;

    @Autowired
    private TestConsumer testConsumer;

    @Test
    public void send_happyPath() throws InterruptedException {
        testConsumer.resetLatch();

        var message = new RecalculatePriceMessage(
                LocalDateTime.now(),
                1, 1, new RecalculatePricePayload(List.of(Service.DKP))
        );

        sender.send(List.of(message));

        boolean messageConsumed = testConsumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertEquals(message, testConsumer.getCurrentMessage());
    }
}