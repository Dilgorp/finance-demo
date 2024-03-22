package ru.dilgorp.pay.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.pay.base.BaseRepositoryTest;
import ru.dilgorp.pay.entity.PaymentEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void findByExternalId_happyPath(){
        var entity = new PaymentEntity(null, UUID.randomUUID(), PayPaymentStatus.PAID);

        paymentRepository.save(entity);

        var result = paymentRepository.findByExternalId(entity.getExternalId());
        entity.setId(result.getId());

        assertEquals(entity, result);
    }

    @Test
    public void findByExternalId_paymentNotFound(){
        var externalId = UUID.randomUUID();

        var result = paymentRepository.findByExternalId(externalId);

        assertNull(result);
    }
}