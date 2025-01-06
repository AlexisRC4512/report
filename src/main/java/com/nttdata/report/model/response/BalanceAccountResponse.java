package com.nttdata.report.model.response;

import com.nttdata.report.model.entity.BalanceAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceAccountResponse {
    private String clientId;
    private List<BalanceAccount> balances;
}
