package ru.dilgorp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.domain.model.PayPayment;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPaymentDto{
    private Long id;
    private UUID externalId;
    private PayPaymentStatus status;

    public static PayPaymentDto from(PayPayment payment) {
        return new PayPaymentDto(payment.id(), payment.externalId(), payment.status());
    }
}
