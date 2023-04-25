package com.demo.lottery.infrastructure.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ballot")
public class BallotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`uuid`", columnDefinition = "UUID")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_uuid", updatable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lottery_uuid", updatable = false)
    private LotteryEntity lottery;

    @Column(name = "is_winning")
    private Boolean isWinning;
}
