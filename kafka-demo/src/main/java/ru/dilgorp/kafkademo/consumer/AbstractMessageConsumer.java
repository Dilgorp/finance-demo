package ru.dilgorp.kafkademo.consumer;

import ru.dilgorp.domain.message.SimpleMessage;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractMessageConsumer {

    protected CountDownLatch latch = new CountDownLatch(0);
    protected LocalDateTime messageTime;
    protected String messageText;

    protected void saveMessage(SimpleMessage message){
        if (latch.getCount() == 0) {
            return;
        }

        messageTime = message.createdAt();
        messageText = message.payload();
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public String getMessageText() {
        return messageText;
    }
}
