package com.programming.creditlimitservice.controller;

import com.programming.creditlimitservice.dto.AccountRequest;
import com.programming.creditlimitservice.dto.CreditRequest;
import com.programming.creditlimitservice.model.CreditLimitOffer;
import com.programming.creditlimitservice.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credit/offer")
public class CreditController {

    private final CreditService creditService;
    @PostMapping
    public ResponseEntity<String> createCreditRequest(@RequestBody CreditRequest creditRequest){
            try {
                 String str = creditService.createCreditRequest(creditRequest);
                 if(str.startsWith("Error"))
                     throw new RuntimeException(str);
                return  ResponseEntity.status(HttpStatus.CREATED).body(str);
            } catch (Exception e) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
    }

    @GetMapping
    public ResponseEntity<List<CreditLimitOffer>> getActiveCreditRequest(@RequestParam("accountId") String accountId,
                                                                         @RequestParam("activationDate")
                                                                          String activeDateString) {
        Date activeDate = null;
        try {
            activeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(activeDateString);

        } catch(ParseException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            List<CreditLimitOffer> listCreditRequest = creditService.getActiveCreditRequest(accountId, activeDate);
            return ResponseEntity.status(HttpStatus.FOUND).body(listCreditRequest);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCreditRequest(@PathVariable Long id, @RequestParam("status") CreditLimitOffer.StatusType status){
        int result = creditService.updateCreditRequest(id, status);
        if(result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credit Request Not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Credit Request Updated.");
    }


}
