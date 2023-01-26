package ru.dilgorp.domain.message;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefaultMessage extends AbstractMessage<String> {
    public DefaultMessage(LocalDateTime createdAt, long dealId, long seq, String payload) {
        super(createdAt, dealId, seq, payload);
    }

    @Override
    public Class<String> payloadClass() {
        return String.class;
    }
}
