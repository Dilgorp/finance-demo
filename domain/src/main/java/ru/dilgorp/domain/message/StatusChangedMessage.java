package ru.dilgorp.domain.message;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import ru.dilgorp.domain.message.payload.StatusChangedPayload;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StatusChangedMessage extends AbstractMessage<StatusChangedPayload> {
    public StatusChangedMessage(@NonNull LocalDateTime createdAt,
                                long dealId,
                                long seq,
                                @NonNull StatusChangedPayload payload) {
        super(createdAt, dealId, seq, payload);
    }

    @Override
    public Class<StatusChangedPayload> payloadClass() {
        return StatusChangedPayload.class;
    }
}
