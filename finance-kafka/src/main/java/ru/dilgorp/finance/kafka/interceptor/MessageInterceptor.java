package ru.dilgorp.finance.kafka.interceptor;

import ru.dilgorp.domain.message.AbstractMessage;

public interface MessageInterceptor {
    public void intercept(AbstractMessage<?> message);
}
