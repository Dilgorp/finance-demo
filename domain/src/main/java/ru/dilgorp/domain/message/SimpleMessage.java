package ru.dilgorp.domain.message;

import java.time.LocalDateTime;

public record SimpleMessage(LocalDateTime createdAt, String payload) {
}
