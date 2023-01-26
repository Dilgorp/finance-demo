package ru.dilgorp.domain.message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class AbstractMessage<T> implements Message<T> {
    private final LocalDateTime createdAt;
    private final long dealId;
    private final long seq;
    private final T payload;
}
