package ru.dilgorp.finance.rabbit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "lock")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor()
public class LockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "deal_id")
    private Long dealId;

    @Column(name = "seq")
    private Long seq;
}
