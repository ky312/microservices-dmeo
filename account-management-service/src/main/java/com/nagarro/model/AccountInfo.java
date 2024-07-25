package com.nagarro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    private String accountNumber;
    private String customerId;
    private BigDecimal amount;
}
