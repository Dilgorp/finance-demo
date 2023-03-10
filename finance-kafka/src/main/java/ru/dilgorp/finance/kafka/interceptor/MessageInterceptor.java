package ru.dilgorp.finance.kafka.interceptor;

import ru.dilgorp.domain.message.AbstractMessage;

public interface MessageInterceptor {
    void intercept(AbstractMessage<?> message);
}
