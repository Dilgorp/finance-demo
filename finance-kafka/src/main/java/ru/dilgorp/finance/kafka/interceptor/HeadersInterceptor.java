package ru.dilgorp.finance.kafka.interceptor;

import org.springframework.messaging.MessageHeaders;

public interface HeadersInterceptor {
    void intercept(MessageHeaders messageHeaders);
}
