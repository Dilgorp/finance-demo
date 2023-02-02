package ru.dilgorp.finance.rabbit.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.dilgorp.domain.enums.PaymentStatus;
import ru.dilgorp.domain.enums.Service;

@Table(name = "payment")
@Entity
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}
