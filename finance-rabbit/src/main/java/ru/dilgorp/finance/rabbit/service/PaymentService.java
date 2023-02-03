package ru.dilgorp.finance.rabbit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.message.AbstractMessage;
import ru.dilgorp.finance.rabbit.entity.PaymentEntity;
import ru.dilgorp.finance.rabbit.repository.PaymentRepository;
import ru.dilgorp.finance.rabbit.service.connector.ShMockConnectorService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final LockService lockService;
    private final ShMockConnectorService shMockConnectorService;
    private final PaymentRepository paymentRepository;

    @Transactional
    public void processMessage(AbstractMessage<?> message){
        long dealId = message.getDealId();
        long seq = message.getSeq();
        lockService.tryLock(dealId, seq);

        var payment = new PaymentEntity(
                null, dealId, seq,
                ru.dilgorp.domain.enums.Service.ONREG,
                PaymentStatus.NOT_PAID, null
        );

        shMockConnectorService.longTime();

        payment.setDate(LocalDateTime.now());
        paymentRepository.save(payment);
    }
}
