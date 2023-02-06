package com.programming.creditlimitservice.service;

import com.programming.creditlimitservice.dto.CreditRequest;
import com.programming.creditlimitservice.model.Account;
import com.programming.creditlimitservice.model.CreditLimitOffer;
import com.programming.creditlimitservice.repository.AccountRepository;
import com.programming.creditlimitservice.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final AccountRepository accountRepository;
    private final CreditRepository creditRepository;

    public String createCreditRequest(CreditRequest creditRequest){
        String accountId = creditRequest.getAccountId();
        Account account = accountRepository.findByAccountId(accountId);
        if(account == null)
            return "Error:AccountId don't exist";
        CreditLimitOffer.LimitType limitType = creditRequest.getLimitType();
        CreditLimitOffer.StatusType statusType = creditRequest.getStatus();
        Double limit = creditRequest.getNewLimit();

        Double prevLimitInserted = creditRepository.findMaxActiveLimitPerAccountByLimitType(accountId,limitType, statusType);
        if(prevLimitInserted != null && prevLimitInserted > limit) {
            return "Error:Limit provided is smaller than existing Limit";
        }
        //The below condition is checked on PreCondition Time Itself
        //if(creditRequest.getOfferExpiryTime().compareTo(creditRequest.getOfferActivationTime()) <=0) {
        //     return "Error: Expiry Date should be greater than Activation Date";
        //}

        CreditLimitOffer creditLimitOffer = new CreditLimitOffer();
        creditLimitOffer.setLimitType(limitType);
        creditLimitOffer.setNewLimit(limit);
        creditLimitOffer.setOfferExpiryTime(creditRequest.getOfferExpiryTime());
        creditLimitOffer.setOfferActivationTime(creditRequest.getOfferActivationTime());
        creditLimitOffer.setAccountId(accountId);
        creditLimitOffer.setStatus(CreditLimitOffer.StatusType.PENDING);

        try {
            creditRepository.save(creditLimitOffer);
            return "Success: Saved";
        } catch(Exception e) {
            return "Error:" + e.getMessage().toString() ;
        }

    }

    public List<CreditLimitOffer> getActiveCreditRequest(String accountId, Date activeDate) {

        CreditLimitOffer.StatusType statusType = CreditLimitOffer.StatusType.ACCEPTED;
        return  creditRepository.findByAccountIdAndActiveDate(accountId, activeDate, statusType);
    }

    public int updateCreditRequest(Long id, CreditLimitOffer.StatusType status) {
        System.out.println("Time for update with the values");
        System.out.println("Id: " + id + "status: " + status);
        return creditRepository.updateCreditRequest(id, status);
    }
}
