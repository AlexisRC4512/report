package com.nttdata.report.service;

import com.nttdata.report.model.entity.Credit;
import com.nttdata.report.model.response.BalanceCreditResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class CreditService {
    @Autowired
    private WebClient webClient;


    public Flux<BalanceCreditResponse> getCreditBalancesByClientId(String clientId) {
        return webClient.get()
                .uri("/api/v1/credit/{id_client}/balances", clientId)
                .retrieve()
                .bodyToFlux(BalanceCreditResponse.class);
    }
    public Flux<Credit> getCreditCardClientId(String clientId) {
        return webClient.get()
                .uri("/api/v1/credit/client/{id_Client}", clientId)
                .retrieve()
                .bodyToFlux(Credit.class);
    }
}
