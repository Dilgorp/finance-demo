package ru.dilgorp.finance.rabbit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.domain.dto.PaymentDto;
import ru.dilgorp.domain.dto.PaymentWithPayDto;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.finance.rabbit.service.PaymentService;
import ru.dilgorp.finance.rabbit.service.connector.PayConnectorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final PayConnectorService payConnectorService;

    @GetMapping("/by_deal/{deal_id}/with_pay")
    @Cacheable("paymentsWithPay")
    public List<PaymentWithPayDto> getByDealWithPay(
            @PathVariable("deal_id") Long dealId,
            @RequestParam(value = "services", required = false, defaultValue = "") List<Service> services
    ) {
        return paymentService.getPayments(dealId)
                .stream()
                .filter(payment -> services.size() == 0 || services.contains(payment.service()))
                .map(payment -> {
                    var payPayment = payConnectorService.getPayment(payment.externalId());
                    return new PaymentWithPayDto(PaymentDto.from(payment), payPayment);
                }).collect(Collectors.toList());
    }
}
