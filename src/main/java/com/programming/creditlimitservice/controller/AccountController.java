package com.programming.creditlimitservice.controller;

import com.programming.creditlimitservice.dto.AccountRequest;
import com.programming.creditlimitservice.model.Account;
import com.programming.creditlimitservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private String getHashCustomerId(String customerId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(customerId.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not find SHA-256 algorithm", e);
        }
    }

    private final AccountService accountService;
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountRequest accountRequest){
        //Preconditions written inside aspects.PreConditionAspect
        accountRequest.setCustomerId(getHashCustomerId(accountRequest.getCustomerId()));
        try {
            accountService.createAccount(accountRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer Account created successfully.");
        } catch(DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Account already exist");
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Account> getAccountByCustomerId(@PathVariable String customerId) {
        String hashCustomerId = getHashCustomerId(customerId);
        Account account = accountService.getAccountByCustomerID(hashCustomerId);

        if(account == null) {
            System.out.println("Not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            account.setCustomerId(customerId);
            return ResponseEntity.ok(account);
        }

    }

}
