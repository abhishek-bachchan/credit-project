package com.programming.creditlimitservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private long id;
    private String accountId;
    private String customerId;
    private double accountLimit;
    private double perTransactionLimit;
    private double lastAccountLimit;
    private double lastPerTransactionLimit;
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountLimitUpdateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date perTransactionLimitUpdateTime;

}
