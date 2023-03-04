package ru.dilgorp.finance.kafka.interceptor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;

import java.util.concurrent.CountDownLatch;

@Getter
@Slf4j
public class TestHeadersInterceptor implements HeadersInterceptor {

    protected CountDownLatch latch = new CountDownLatch(0);

    private MessageHeaders headers;
    @Override
    public void intercept(MessageHeaders headers) {
        log.info("Headers BEFORE intercept: latch = {}", latch);

        this.headers = headers;
        latch.countDown();

        log.info("Headers AFTER intercept: latch = {}", latch);
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
