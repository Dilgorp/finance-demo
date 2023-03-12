package ru.dilgorp.domain.dto;

public record PaymentWithPayDto(
        PaymentDto payment,
        PayPaymentDto payPayment
) {
}
