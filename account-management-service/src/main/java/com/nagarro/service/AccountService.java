package com.nagarro.service;

import com.nagarro.model.Account;
import com.nagarro.model.AccountInfo;
import com.nagarro.model.Customer;
import com.nagarro.model.CustomerAccountInfo;

import java.util.List;

public interface AccountService {
    Account createAccount(Customer customer);

    String deleteAccount(Long id);

    Account getAccountInfoByCustomerId(Long id);

    Account addMoneyToAccount(AccountInfo accountInfo);

    Account withdrawMoneyFromAccount(AccountInfo accountInfo);

    CustomerAccountInfo getAccountInfo(String accountNumber);

    String deleteAccountByAccountNumber(String accountNumber);

    List<Account> getAllAccounts();
}
