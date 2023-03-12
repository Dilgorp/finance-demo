package ru.dilgorp.pay.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.domain.model.PayPayment;
import ru.dilgorp.pay.repository.PaymentRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PayPayment getPayment(UUID externalId) {
        return paymentRepository.findByExternalId(externalId).toModel();
    }
}
