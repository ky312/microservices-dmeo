package com.nagarro.controller;


import com.nagarro.model.Account;
import com.nagarro.model.AccountInfo;
import com.nagarro.model.Customer;
import com.nagarro.model.CustomerAccountInfo;
import com.nagarro.model.response.SuccessResponse;
import com.nagarro.service.AccountService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping
    public ResponseEntity<SuccessResponse<List<Account>>> getAllAccounts(){
        return new ResponseEntity<>(SuccessResponse.<List<Account>>builder()
                .data(accountService.getAllAccounts())
                .status(HttpStatus.CREATED.value())
                .message("success")
                .build(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<SuccessResponse<Account>> createAccount(@RequestBody Customer customer){
        return new ResponseEntity<>(SuccessResponse.<Account>builder()
                .data(accountService.createAccount(customer))
                .status(HttpStatus.CREATED.value())
                .message("success")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/info/{accountNumber}")
    @CircuitBreaker(name = "customerServiceBreaker", fallbackMethod = "getCustomerAccountInfoFallback")
    public ResponseEntity<SuccessResponse<CustomerAccountInfo>> getAccountInfo(@PathVariable String accountNumber){
        return new ResponseEntity<>(SuccessResponse.<CustomerAccountInfo>builder()
                .data(accountService.getAccountInfo(accountNumber))
                .status(HttpStatus.OK.value())
                .message("success")
                .build(), HttpStatus.OK);
    }


    public ResponseEntity<SuccessResponse<CustomerAccountInfo>> getCustomerAccountInfoFallback(String accountNumber, Exception ex) {
        Account account = Account.
        builder()
                .id(0L)
                .balance(BigDecimal.ZERO)
                .accountType("DUMMY")
                .accountNumber("DUMMY000")
                .customerId("DUMMY123")
                .build();
        Customer customer = Customer.builder()
                .id(0L)
                .email("dummy@mail.com")
                .firstName("Dummy")
                .lastName("Joe")
                .phoneNumber("0000000000")
                .build();

        CustomerAccountInfo customerAccountInfo = CustomerAccountInfo.builder()
                .account(account)
                .customer(customer)
                .build();

        return new ResponseEntity<>(SuccessResponse.<CustomerAccountInfo>builder()
                .data(customerAccountInfo)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Dummy account info created because customer services is down.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/deposit")
    public ResponseEntity<SuccessResponse<Account>> addAccountBalance(@RequestBody AccountInfo accountInfo) {
        return new ResponseEntity<>(SuccessResponse.<Account>builder()
                .data(accountService.addMoneyToAccount(accountInfo))
                .status(HttpStatus.OK.value())
                .message("success")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<SuccessResponse<Account>> withdrawAccountBalance(@RequestBody AccountInfo accountInfo) {
        return new ResponseEntity<>(SuccessResponse.<Account>builder()
                .data(accountService.withdrawMoneyFromAccount(accountInfo))
                .status(HttpStatus.OK.value())
                .message("success")
                .build(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> deleteAccount(@PathVariable Long id){
        return new ResponseEntity<>(SuccessResponse.<String>builder()
                .data(accountService.deleteAccount(id))
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .build(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<SuccessResponse<String>> deleteAccount(@PathVariable String accountNumber){
        return new ResponseEntity<>(SuccessResponse.<String>builder()
                .data(accountService.deleteAccountByAccountNumber(accountNumber))
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .build(), HttpStatus.NO_CONTENT);
    }
}
