package com.nttdata.report.service;


import com.nttdata.report.model.entity.CreditCard;
import com.nttdata.report.model.response.BalanceCreditCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class CreditCardService {
    @Autowired
    private WebClient webClient;

    public Flux<BalanceCreditCardResponse> getCreditCardBalancesByClientId(String clientId) {
        return webClient.get()
                .uri("/api/v1/CreditCard/{id_client}/balances", clientId)
                .retrieve()
                .bodyToFlux(BalanceCreditCardResponse.class);
    }
    public Flux<CreditCard> getCreditCardClientId(String clientId) {
        return webClient.get()
                .uri("/api/v1/CreditCard/client/{id_Client}", clientId)
                .retrieve()
                .bodyToFlux(CreditCard.class);
    }
}
