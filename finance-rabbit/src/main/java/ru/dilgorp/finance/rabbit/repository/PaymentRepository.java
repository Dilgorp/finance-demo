package ru.dilgorp.finance.rabbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.finance.rabbit.entity.PaymentEntity;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findAllByDealId(Long dealId);
}
