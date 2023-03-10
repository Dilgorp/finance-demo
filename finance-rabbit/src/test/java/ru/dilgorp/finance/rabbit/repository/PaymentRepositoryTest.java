package ru.dilgorp.finance.rabbit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.finance.rabbit.base.BaseRepositoryTest;
import ru.dilgorp.finance.rabbit.entity.PaymentEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void save_happyPath() {
        var payment = new PaymentEntity(null, 1L, 1L, Service.ONREG,
                PaymentStatus.NOT_PAID, LocalDateTime.now(), UUID.randomUUID());

        var savedPayment = paymentRepository.save(payment);
        payment.setId(savedPayment.getId());

        assertEquals(payment, savedPayment);
    }
}