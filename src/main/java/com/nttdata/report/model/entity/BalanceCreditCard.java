package com.nttdata.report.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Represents the balance of a client.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BalanceCreditCard {
    /**
     * Account ID.
     */
    private String accountId;

    /**
     * Credit balance of the client.
     */
    private double creditBalance;

    /**
     * Date of the balance.
     */
    private Date date;
    /**
     * Type of the account.
     */
    private String typeCredit;

    public BalanceCreditCard(String id, double balance) {
    }


}
