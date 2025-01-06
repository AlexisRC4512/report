package com.nttdata.report.util;

import com.nttdata.report.model.response.*;
import reactor.core.publisher.Mono;

import java.util.List;

public class BalanceConverter {
    public static Mono<BalanceResponse> balanceResponse(List<BalanceAccountResponse>accountResponseList, List<BalanceCreditResponse>creditResponseList,
                                                        List<BalanceCreditCardResponse>creditCardReponseList, String clienId) {
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setClientId(clienId);
        balanceResponse.setBalancesAccount(accountResponseList);
        balanceResponse.setBalancesCreditCard(creditCardReponseList);
        balanceResponse.setBalancesCredit(creditResponseList);
        return Mono.just(balanceResponse);
    }
}
