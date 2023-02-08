package ru.dilgorp.finance.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.finance.kafka.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
