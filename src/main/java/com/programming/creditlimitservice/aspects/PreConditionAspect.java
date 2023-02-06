package com.programming.creditlimitservice.aspects;

import com.programming.creditlimitservice.dto.AccountRequest;
import com.programming.creditlimitservice.dto.CreditRequest;
import com.programming.creditlimitservice.model.CreditLimitOffer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;

@Aspect
@Component
public class PreConditionAspect {

    @Before("execution(* com.programming.creditlimitservice.controller.*.create*(..))")
    public void checkPreConditions(JoinPoint joinPoint) {
        Object request = joinPoint.getArgs()[0];

        if(!checkRequest(request)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid request. Please check!");
        }
    }

    @Before("execution(* com.programming.creditlimitservice.controller.AccountController.get*(..))")
    public void checkPreConditionsGetMethod(JoinPoint joinPoint) {
        Object request = joinPoint.getArgs()[0];

        if(!checkRequest(request)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid request. Please check!");
        }
    }

    private boolean checkRequest(Object request) {

        if(request instanceof CreditRequest) {
            if(((CreditRequest) request).getAccountId().isEmpty() || ((CreditRequest) request).getAccountId()=="")
                return false;
            if(((CreditRequest) request).getLimitType() == null)
                return false;
            if(((CreditRequest) request).getNewLimit() == null)
                return false;
            if(!EnumSet.of(CreditLimitOffer.LimitType.ACCOUNT_LIMIT,CreditLimitOffer.LimitType.PER_TRANSACTION_LIMIT )
                    .contains(((CreditRequest) request).getLimitType()))
                return false;
            if(((CreditRequest) request).getOfferActivationTime() == null || ((CreditRequest) request)
                                                                                    .getOfferExpiryTime()== null)
                return false;
            if(((CreditRequest) request).getOfferExpiryTime().compareTo(((CreditRequest) request).getOfferActivationTime()) <=0)
                return false;
            return true;


        }
        else if(request instanceof AccountRequest) {
            if(((AccountRequest) request).getCustomerId().isEmpty() || ((AccountRequest) request).getCustomerId()=="")
                return false;
            return true;
        }
        return true;

    }
}
