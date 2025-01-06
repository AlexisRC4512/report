package com.nttdata.report.model.response;

import com.nttdata.report.model.entity.BalanceCreditCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceCreditCardResponse {
    private String clientId;
    private List<BalanceCreditCard> balances;
}
