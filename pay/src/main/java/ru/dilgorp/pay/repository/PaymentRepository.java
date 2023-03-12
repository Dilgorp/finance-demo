package ru.dilgorp.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.pay.entity.PaymentEntity;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    PaymentEntity findByExternalId(UUID externalId);
}
