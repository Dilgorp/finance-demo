package ru.dilgorp.finance.kafka.interceptor;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;

import java.util.concurrent.CountDownLatch;

@Component
@Getter
public class TestMessageInterceptor implements MessageInterceptor {

    protected CountDownLatch latch = new CountDownLatch(0);

    private AbstractMessage<?> message;
    @Override
    public void intercept(AbstractMessage<?> message) {
        this.message = message;
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
