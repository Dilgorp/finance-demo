package ru.dilgorp.domain.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class AbstractMessage<T> implements Message<T> {
    private final LocalDateTime createdAt;
    private final long dealId;
    private final long seq;
    private final T payload;
}
