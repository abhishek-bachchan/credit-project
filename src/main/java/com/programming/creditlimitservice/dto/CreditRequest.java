package com.programming.creditlimitservice.dto;
import com.programming.creditlimitservice.model.CreditLimitOffer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequest {
    private long id;
    private String accountId;

    private CreditLimitOffer.LimitType limitType;
    private Date offerActivationTime;
    private Date offerExpiryTime;
    private Double newLimit;
    private CreditLimitOffer.StatusType status;
}
