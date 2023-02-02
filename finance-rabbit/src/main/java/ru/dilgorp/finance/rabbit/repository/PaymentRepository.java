package ru.dilgorp.finance.rabbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.finance.rabbit.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
