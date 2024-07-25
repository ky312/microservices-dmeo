package com.nagarro.controller;

import com.nagarro.model.Account;
import com.nagarro.model.Customer;
import com.nagarro.model.CustomerAccountInfo;
import com.nagarro.model.response.SuccessResponse;
import com.nagarro.service.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<Customer>>> getAllCustomer(){
        return new ResponseEntity<>(SuccessResponse.<List<Customer>>builder()
                .data(customerService.getAllCustomer())
                .status(HttpStatus.OK.value())
                .message("success")
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<Customer>> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(SuccessResponse.<Customer>builder()
                .data(customerService.getCustomerById(id))
                .status(HttpStatus.OK.value())
                .message("success")
                .build(), HttpStatus.OK);
    }

    @PostMapping
    @CircuitBreaker(name = "accountServiceBreaker", fallbackMethod = "accountCreationFallback")
    public ResponseEntity<SuccessResponse<CustomerAccountInfo>> createCustomer(@RequestBody Customer customer){
        return new ResponseEntity<>(SuccessResponse.<CustomerAccountInfo>builder()
                .data(customerService.createCustomer(customer))
                .status(HttpStatus.CREATED.value())
                .message("success")
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<SuccessResponse<CustomerAccountInfo>> accountCreationFallback(Customer cust, Exception ex) {
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
                .status(HttpStatus.OK.value())
                .message("Dummy account info created because account services is down.")
                .build(), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Customer>> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return new ResponseEntity<>(SuccessResponse.<Customer>builder()
                .data(customerService.updateCustomer(id, customer))
                .status(HttpStatus.OK.value())
                .message("success")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "accountServiceBreaker", fallbackMethod = "accountDeletionFallback")
    public ResponseEntity<SuccessResponse<String>> deleteCustomer(@PathVariable Long id){
        return new ResponseEntity<>(SuccessResponse.<String>builder()
                .data(customerService.deleteCustomer(id))
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .build(), HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<SuccessResponse<String>> accountDeletionFallback(Long id, Exception ex){
        return new ResponseEntity<>(SuccessResponse.<String>builder()
                .data("Service is down")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Account can not be deleted because account services is down.")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
