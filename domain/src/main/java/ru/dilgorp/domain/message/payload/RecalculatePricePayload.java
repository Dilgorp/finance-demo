package ru.dilgorp.domain.message.payload;

import ru.dilgorp.domain.enums.Service;

import java.util.List;

public record RecalculatePricePayload(List<Service> services) {
}
