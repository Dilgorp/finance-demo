package ru.dilgorp.finance.rabbit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;
import ru.dilgorp.domain.model.Payment;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "payment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "deal_id")
    private Long dealId;

    @Column(name = "seq")
    private Long seq;

    @Column(name = "service")
    @Enumerated(value = EnumType.STRING)
    private Service service;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "external_id")
    private UUID externalId;

    public Payment toModel(){
        return new Payment(id, dealId, seq, service, status, date, externalId);
    }
}
