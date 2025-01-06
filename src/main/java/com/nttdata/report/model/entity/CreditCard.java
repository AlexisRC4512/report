package com.nttdata.report.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreditCard {
    private String id;
    private String type;
    private double creditLimit;
    private double availableBalance;
    private Date issueDate;
    private Date expirationDate;
    private String clientId;
    private List<Transaction> transactions;
}
