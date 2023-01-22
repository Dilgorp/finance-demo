package ru.dilgorp.shmock.service.factory;

import ru.dilgorp.domain.message.AbstractMessage;

public interface MessageProducer<P> {
    public Class<P> payloadClass();

    public AbstractMessage<P> produce(long dealId, long seq);
}
