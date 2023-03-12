package ru.dilgorp.pay.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.domain.enums.PayPaymentStatus;
import ru.dilgorp.domain.model.PayPayment;

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

    @Column(name = "external_id")
    private UUID externalId;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private PayPaymentStatus status;

    public PayPayment toModel(){
        return new PayPayment(id, externalId, status);
    }
}
