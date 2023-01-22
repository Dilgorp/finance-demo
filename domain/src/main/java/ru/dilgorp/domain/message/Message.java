package ru.dilgorp.domain.message;

public interface Message<T> {
    public Class<T> payloadClass();
    public T getPayload();
}
