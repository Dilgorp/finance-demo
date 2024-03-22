package ru.dilgorp.pay.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.domain.dto.PayPaymentDto;
import ru.dilgorp.pay.service.PaymentService;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/external_id/{external_id}")
    @SneakyThrows
    public PayPaymentDto getByExternalId(
            @PathVariable("external_id") UUID externalId) {

        Thread.sleep(500);
        return PayPaymentDto.from(paymentService.getPayment(externalId));
    }

    @PostMapping("/external_id/{external_id}")
    public PayPaymentDto postByExternalId(@PathVariable("external_id") UUID externalId) {
        return PayPaymentDto.from(paymentService.newPayment(externalId));
    }
}
