package com.programming.creditlimitservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(indexes = {@Index(name = "customer_id_index", columnList = "customerId")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String accountId;

    // The customerID should be unique like Pan Number
    @Column(unique = true, nullable = false)
    private String customerId;
    private double accountLimit;
    private double perTransactionLimit;
    private double lastAccountLimit;
    private double lastPerTransactionLimit;

    @Temporal(TemporalType.TIMESTAMP)
    private Date accountLimitUpdateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date perTransactionLimitUpdateTime;
    public Account(String accountId, String customerId){
        this.customerId = customerId;
        this.accountId = accountId;
        this.accountLimit = 0;
        this.perTransactionLimit = 0;
        this.lastAccountLimit = 0;
        this.lastPerTransactionLimit = 0;
        this.accountLimitUpdateTime = null;
        this.perTransactionLimitUpdateTime = null;
    }

}
