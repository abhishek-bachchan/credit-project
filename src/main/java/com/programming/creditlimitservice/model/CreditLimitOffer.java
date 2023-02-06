package com.programming.creditlimitservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table( indexes = {@Index(name = "account_id_index", columnList = "accountId")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditLimitOffer {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private LimitType limitType;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date offerActivationTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date offerExpiryTime;

    @Column(nullable = false)
    private double newLimit;

    @Column(nullable = false)
    private StatusType status;

    public enum LimitType {
        ACCOUNT_LIMIT, PER_TRANSACTION_LIMIT
    }
    public enum StatusType {
        PENDING, ACCEPTED, REJECTED
    }
}
