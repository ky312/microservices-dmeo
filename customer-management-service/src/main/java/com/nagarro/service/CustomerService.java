package com.nagarro.service;


import com.nagarro.model.Customer;
import com.nagarro.model.CustomerAccountInfo;

import java.util.List;

public interface CustomerService {

    CustomerAccountInfo createCustomer(Customer customer);

    List<Customer> getAllCustomer();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id, Customer customer);

    String deleteCustomer(Long id);
}
