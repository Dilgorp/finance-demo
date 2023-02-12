package ru.dilgorp.finance.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.dilgorp.domain.message.AbstractMessage;

@Component
@Slf4j
public class LogMessageInterceptor implements MessageInterceptor{
    @Override
    public void intercept(AbstractMessage<?> message) {
        log.info("Received message sh event: {}", message);
    }
}
