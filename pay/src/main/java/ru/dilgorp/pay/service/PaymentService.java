package ru.dilgorp.pay.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.domain.model.PayPayment;
import ru.dilgorp.pay.entity.PaymentEntity;
import ru.dilgorp.pay.repository.PaymentRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public PayPayment getPayment(UUID externalId) {
        return paymentRepository.findByExternalId(externalId).toModel();
    }

    @Transactional
    public PayPayment newPayment(UUID externalId) {
        var paymentEntity = paymentRepository.findByExternalId(externalId);
        if (paymentEntity == null) {
            paymentEntity = paymentRepository.save(
                    new PaymentEntity(null, externalId, PayPaymentStatus.PROCESSING));
        }

        return paymentEntity.toModel();
    }
}
