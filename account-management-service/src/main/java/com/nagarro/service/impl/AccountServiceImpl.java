package com.nagarro.service.impl;

import com.nagarro.exception.AccountNotFoundException;
import com.nagarro.exception.LowBalanceException;
import com.nagarro.feigns.CustomerService;
import com.nagarro.model.Account;
import com.nagarro.model.AccountInfo;
import com.nagarro.model.Customer;
import com.nagarro.model.CustomerAccountInfo;
import com.nagarro.model.response.SuccessResponse;
import com.nagarro.repository.AccountRepository;
import com.nagarro.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final CustomerService customerService;

    @Override
    public Account createAccount(Customer customer) {
        String accountNumber = "ACC" + System.nanoTime();
        Account account = Account.builder()
                .customerId("CUST" + customer.getId())
                .accountNumber(accountNumber)
                .accountType("SAVING")
                .balance(BigDecimal.ZERO)
                .build();
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountInfoByCustomerId(Long id){
        String customerID = "CUST" + id;
        return accountRepository.findByCustomerId(customerID).orElseThrow(() -> new AccountNotFoundException("No account present with id: " + id));
    }

    @Override
    public Account addMoneyToAccount(AccountInfo accountInfo) {
        Account account = accountRepository.findByCustomerId(accountInfo.getCustomerId()).orElseThrow(() -> new AccountNotFoundException("No account present with id: " + accountInfo.getAccountNumber()));
        account.setBalance(account.getBalance().add(accountInfo.getAmount()));
        return accountRepository.save(account);
    }

    @Override
    public Account withdrawMoneyFromAccount(AccountInfo accountInfo) {
        Account account = accountRepository.findByCustomerId(accountInfo.getCustomerId()).orElseThrow(() -> new AccountNotFoundException("No account present with id: " + accountInfo.getAccountNumber()));
        if(account.getBalance().compareTo(accountInfo.getAmount()) >= 0) {
            account.setBalance(account.getBalance().subtract(accountInfo.getAmount()));
        } else {
            throw new LowBalanceException("Insufficient Balance!!");
        }
        return accountRepository.save(account);
    }

    @Override
    public CustomerAccountInfo getAccountInfo(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("No account present with account number: " + accountNumber));
        Long customerId = Long.valueOf(Character.toString(account.getCustomerId().charAt(account.getCustomerId().length() - 1)));
        ResponseEntity<SuccessResponse<Customer>> customer = customerService.getCustomerById(customerId);
        return new CustomerAccountInfo(account, customer.getBody().getData());
    }


    @Override
    public String deleteAccount(Long id) {
        Account account = getAccountInfoByCustomerId(id);
        accountRepository.delete(account);
        return String.format("account with id %d deleted!!", id);
    }

    @Override
    public String deleteAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("No account present with id: " + accountNumber));
        accountRepository.delete(account);
        return "Account deleted with account number: " + accountNumber;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
