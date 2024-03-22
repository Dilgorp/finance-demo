package ru.dilgorp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentWithPayDto {
    private PaymentDto payment;
    private PayPaymentDto payPayment;
}
