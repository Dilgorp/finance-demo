package ru.dilgorp.domain.dto;

import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.model.Payment;

import java.time.LocalDateTime;

public record PaymentDto(
        Long id,
        Long dealId,
        Long seq,
        Service service,
        PaymentStatus status,
        LocalDateTime date
) {
    public static PaymentDto from(Payment payment){
        return new PaymentDto(payment.id(), payment.dealId(), payment.seq(),
                payment.service(), payment.status(), payment.date());
    }
}
