package com.nagarro.service.impl;


import com.nagarro.exception.CustomerNotFoundException;
import com.nagarro.feign.AccountService;
import com.nagarro.model.Account;
import com.nagarro.model.Customer;
import com.nagarro.model.CustomerAccountInfo;
import com.nagarro.model.response.SuccessResponse;
import com.nagarro.repository.CustomerRepository;
import com.nagarro.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final AccountService accountService;

    @Override
    public CustomerAccountInfo createCustomer(Customer customer) {
        Customer dbCustomer = repository.save(customer);
        ResponseEntity<SuccessResponse<Account>> account = accountService.createAccount(dbCustomer);
        return new CustomerAccountInfo(dbCustomer, account.getBody().getData());
    }

    @Override
    public List<Customer> getAllCustomer() {
        return repository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id " + id));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        return repository.save(existingCustomer);
    }

    @Override
    public String deleteCustomer(Long id) {
        accountService.deleteAccount(id);
        repository.delete(getCustomerById(id));
        return "Customer deleted with id: " + id;
    }
}
