package ru.dilgorp.domain.message;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import ru.dilgorp.domain.message.payload.RecalculatePricePayload;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString
public class RecalculatePriceMessage extends AbstractMessage<RecalculatePricePayload> {
    public RecalculatePriceMessage(@NonNull LocalDateTime createdAt,
                                   long dealId,
                                   long seq,
                                   @NonNull RecalculatePricePayload payload) {
        super(createdAt, dealId, seq, payload);
    }

    @Override
    public Class<RecalculatePricePayload> payloadClass() {
        return RecalculatePricePayload.class;
    }
}
