package com.programming.creditlimitservice.service;

import com.programming.creditlimitservice.dto.AccountRequest;
import com.programming.creditlimitservice.model.Account;
import com.programming.creditlimitservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void createAccount(AccountRequest accountRequest){
        String accountId = UUID.randomUUID().toString();
        Account account = new Account(accountId, accountRequest.getCustomerId());
        accountRepository.save(account);
    }

    public Account getAccountByCustomerID(String customerID){
        return  accountRepository.findByCustomerId(customerID);
    }

}
