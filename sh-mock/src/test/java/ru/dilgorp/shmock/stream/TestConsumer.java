package ru.dilgorp.shmock.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

@Component("consumer-test")
@Slf4j
public class TestConsumer implements Consumer<AbstractMessage<?>> {

    private CountDownLatch latch = new CountDownLatch(0);

    private AbstractMessage<?> currentMessage;

    @Override
    public void accept(AbstractMessage<?> message) {
        currentMessage = message;
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public AbstractMessage<?> getCurrentMessage() {
        return currentMessage;
    }
}
