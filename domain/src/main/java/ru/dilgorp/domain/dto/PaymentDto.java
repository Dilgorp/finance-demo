package ru.dilgorp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.model.Payment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto{

    private Long id;
    private Long dealId;
    private Long seq;
    private Service service;
    private PaymentStatus status;
    private LocalDateTime date;

    public static PaymentDto from(Payment payment){
        return new PaymentDto(payment.id(), payment.dealId(), payment.seq(),
                payment.service(), payment.status(), payment.date());
    }
}
