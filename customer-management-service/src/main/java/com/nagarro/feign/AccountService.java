package com.nagarro.feign;

import com.nagarro.model.Account;
import com.nagarro.model.Customer;
import com.nagarro.model.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountService {

    @GetMapping("/api/accounts/ping")
    String ping();

    @PostMapping("/api/accounts")
    ResponseEntity<SuccessResponse<Account>> createAccount(@RequestBody Customer customer);

    @DeleteMapping("/api/accounts/{id}")
    ResponseEntity<SuccessResponse<String>> deleteAccount(@PathVariable Long id);
}
