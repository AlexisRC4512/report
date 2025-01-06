package com.nttdata.report.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceResponse {
    private String clientId;
    private List<BalanceAccountResponse>balancesAccount;
    private List<BalanceCreditCardResponse>balancesCreditCard;
    private List<BalanceCreditResponse>balancesCredit;
}
