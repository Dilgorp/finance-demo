package ru.dilgorp.finance.rabbit.interceptor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.dilgorp.domain.message.AbstractMessage;

import java.util.concurrent.CountDownLatch;

@Getter
@Slf4j
public class TestMessageInterceptor implements MessageInterceptor {

    protected CountDownLatch latch = new CountDownLatch(0);

    private AbstractMessage<?> message;

    @Override
    public void intercept(AbstractMessage<?> message) {
        log.info("Message BEFORE intercept: latch = {}", latch);
        this.message = message;
        latch.countDown();
        log.info("Message AFTER intercept: latch = {}", latch);
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
        log.info("TestMessageInterceptor#resetLatch: latch = {}", latch);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
