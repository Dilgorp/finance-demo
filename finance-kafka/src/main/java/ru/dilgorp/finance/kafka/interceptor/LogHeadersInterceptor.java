package ru.dilgorp.finance.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogHeadersInterceptor implements HeadersInterceptor{
    @Override
    public void intercept(MessageHeaders messageHeaders) {
        log.info("Received message header: {}", messageHeaders);
    }
}
