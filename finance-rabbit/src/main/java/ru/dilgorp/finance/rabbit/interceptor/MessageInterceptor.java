package ru.dilgorp.finance.rabbit.interceptor;

import ru.dilgorp.domain.message.AbstractMessage;

public interface MessageInterceptor {
    void intercept(AbstractMessage<?> message);
}
