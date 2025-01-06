package com.nttdata.report.service;

import com.nttdata.report.model.entity.Account;
import com.nttdata.report.model.entity.Commission;
import com.nttdata.report.model.response.BalanceAccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Slf4j
@Service
public class AccountService {
    @Autowired
    private WebClient webClient;



    public Flux<BalanceAccountResponse> getAccountBalancesByClientId(String clientId) {
        return webClient.get()
                .uri("/api/v1/account/{id_client}/balances", clientId)
                .retrieve()
                .bodyToFlux(BalanceAccountResponse.class);
    }
    public Flux<Commission> getCommissionsByAccountIdAndDateRange(String accountId, LocalDate startDate, LocalDate endDate) {
        log.info("Formatted startDate: {}", startDate);
        log.info("Formatted endDate: {}", endDate);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedStartDate = startDate.format(dateFormatter);
        String formattedEndDate = endDate.format(dateFormatter);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/account/{id_client}/commissions")
                        .queryParam("startDate", formattedStartDate)
                        .queryParam("endDate", formattedEndDate)
                        .build(accountId))
                .retrieve()
                .bodyToFlux(Commission.class);
    }

    public Flux<Account> getAccountClientId(String clientId) {
        return webClient.get()
                .uri("/api/v1/account/client/{id_Client}", clientId)
                .retrieve()
                .bodyToFlux(Account.class);
    }
}
