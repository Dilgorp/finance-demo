package ru.dilgorp.kafkademo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dilgorp.domain.message.SimpleMessage;
import ru.dilgorp.kafkademo.base.BaseStreamTest;
import ru.dilgorp.kafkademo.consumer.MessageConsumer;
import ru.dilgorp.kafkademo.producer.MessageProducer;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KafkaStreamTest extends BaseStreamTest {

    @Autowired
    private MessageProducer producer;

    @Autowired
    private MessageConsumer consumer;

    @Test
    public void streamTest() throws InterruptedException {
        consumer.resetLatch();

        LocalDateTime now = LocalDateTime.now();
        String payload = "payload";
        var message = new SimpleMessage(now, payload);

        producer.send(message);

        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertEquals(now, consumer.getMessageTime());
        assertEquals(payload, consumer.getMessageText());
    }
}
