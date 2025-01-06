package com.nttdata.report.service.impl;

import com.nttdata.report.model.entity.Account;
import com.nttdata.report.model.entity.Credit;
import com.nttdata.report.model.entity.CreditCard;
import com.nttdata.report.model.exception.AccountNotFoundException;
import com.nttdata.report.model.response.*;
import com.nttdata.report.service.*;
import com.nttdata.report.util.ComissionConverter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private CreditCardService creditCardService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CreditService creditService;


    @Override
    @CircuitBreaker(name = "report", fallbackMethod = "fallbackGetBalancesByClientId")
    @TimeLimiter(name = "report")
    public Mono<BalanceResponse> getBalancesByClientId(String clientId) {
        Mono<List<BalanceCreditCardResponse>> creditCardsMono = creditCardService.getCreditCardBalancesByClientId(clientId)
                .collectList()
                .onErrorResume(e -> {
                    log.error("Error fetching credit cards for client ID: {}", clientId, e);
                    return Mono.just(Collections.emptyList());
                });

        Mono<List<BalanceAccountResponse>> accountsMono = accountService.getAccountBalancesByClientId(clientId)
                .collectList()
                .onErrorResume(e -> {
                    log.error("Error fetching accounts for client ID: {}", clientId, e);
                    return Mono.just(Collections.emptyList());
                });

        Mono<List<BalanceCreditResponse>> creditsMono = creditService.getCreditBalancesByClientId(clientId)
                .collectList()
                .onErrorResume(e -> {
                    log.error("Error fetching credits for client ID: {}", clientId, e);
                    return Mono.just(Collections.emptyList());
                });

        return Mono.zip(creditCardsMono, accountsMono, creditsMono)
                .map(tuple -> {
                    List<BalanceCreditCardResponse> creditCards = tuple.getT1();
                    List<BalanceAccountResponse> accounts = tuple.getT2();
                    List<BalanceCreditResponse> credits = tuple.getT3();

                    BalanceResponse response = new BalanceResponse();
                    response.setClientId(clientId);
                    response.setBalancesCreditCard(creditCards);
                    response.setBalancesAccount(accounts);
                    response.setBalancesCredit(credits);

                    return response;
                })
                .doOnError(e -> log.error("Error getting report balance for client ID: {}", clientId, e))
                .onErrorMap(e -> new Exception("Error getting report balance for client ID: " + clientId, e));
    }

    @Override
    @CircuitBreaker(name = "report", fallbackMethod = "fallbackGetCommissionByProductId")
    @TimeLimiter(name = "report")
    public Mono<ComissionResponse> getCommissionByProductId(String productId, LocalDate startDate, LocalDate endDate) {
        return accountService.getCommissionsByAccountIdAndDateRange(productId, startDate, endDate)
                .collectList()
                .flatMap(ComissionConverter::toComissionResponse)
                .switchIfEmpty(Mono.error(new AccountNotFoundException("Commission not found with product ID: " + productId)))
                .doOnError(e -> log.error("Error getting commission for product ID: {}", productId, e))
                .onErrorMap(e -> new Exception("Error getting commission for product ID: " + productId, e));
    }

    @Override
    @CircuitBreaker(name = "report", fallbackMethod = "fallbackGetClientSummary")
    @TimeLimiter(name = "report")
    public Mono<Map<String, Object>> getClientSummary(String clientId) {
        Mono<List<Credit>> creditsMono = creditService.getCreditCardClientId(clientId)
                .collectList()
                .onErrorResume(e -> {
                    log.error("Error fetching credits for clientId: {}", clientId, e);
                    return Mono.just(Collections.emptyList());
                });

        Mono<List<CreditCard>> creditCardsMono = creditCardService.getCreditCardClientId(clientId)
                .collectList()
                .onErrorResume(e -> {
                    log.error("Error fetching credit cards for clientId: {}", clientId, e);
                    return Mono.just(Collections.emptyList());
                });

        Mono<List<Account>> accountsMono = accountService.getAccountClientId(clientId)
                .collectList()
                .onErrorResume(e -> {
                    log.error("Error fetching accounts for clientId: {}", clientId, e);
                    return Mono.just(Collections.emptyList());
                });

        return Mono.zip(creditsMono, creditCardsMono, accountsMono)
                .map(tuple -> {
                    Map<String, Object> summary = new HashMap<>();
                    summary.put("credits", tuple.getT1());
                    summary.put("creditCards", tuple.getT2());
                    summary.put("accounts", tuple.getT3());
                    return summary;
                });
    }

    public Mono<BalanceResponse> fallbackGetBalancesByClientId(Exception exception) {
        log.error("Fallback method for getBalancesByClientId", exception);
        return  Mono.error(new Exception("Fallback method for getBalancesByClientId"));
    }

    public Mono<ComissionResponse> fallbackGetCommissionByProductId(Exception exception) {
        log.error("Fallback method for getCommissionByProductId", exception);
        return  Mono.error(new Exception("Fallback method for getCommissionByProductId"));
    }

    public Mono<Void> fallbackGetClientSummary(Exception exception) {
        log.error("Fallback method for getClientSummary", exception);
        return  Mono.error(new Exception("Fallback method for getClientSummary"));
    }
}
