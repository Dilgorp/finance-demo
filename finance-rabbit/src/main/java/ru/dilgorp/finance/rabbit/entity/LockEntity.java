package ru.dilgorp.finance.rabbit.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "lock")
@Entity
@Data
public class LockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "deal_id")
    private Long dealId;

    @Column(name = "seq")
    private Long seq;
}
