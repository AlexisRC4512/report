package com.nttdata.report.model.response;

import com.nttdata.report.model.entity.BalanceCredit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceCreditResponse {
    private String clientId;
    private List<BalanceCredit> balances;
}
