package com.nagarro.feigns;


import com.nagarro.model.Customer;
import com.nagarro.model.response.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerService {

    @GetMapping("/api/customers/{id}")
    ResponseEntity<SuccessResponse<Customer>> getCustomerById(@PathVariable Long id);

}
