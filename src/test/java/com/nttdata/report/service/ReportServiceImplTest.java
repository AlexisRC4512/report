package com.nttdata.report.service;

import com.nttdata.report.model.entity.*;
import com.nttdata.report.model.response.*;
import com.nttdata.report.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import static org.mockito.Mockito.when;

public class ReportServiceImplTest {

    @Mock
    private CreditCardService creditCardService;

    @Mock
    private AccountService accountService;

    @Mock
    private CreditService creditService;
    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testGetBalancesByClientIdSuccess() {
        String clientId = "client123";

        when(creditCardService.getCreditCardBalancesByClientId(clientId))
                .thenReturn(Flux.just(new BalanceCreditCardResponse()));
        when(accountService.getAccountBalancesByClientId(clientId))
                .thenReturn(Flux.just(new BalanceAccountResponse()));
        when(creditService.getCreditBalancesByClientId(clientId))
                .thenReturn(Flux.just(new BalanceCreditResponse()));

        Mono<BalanceResponse> result = reportService.getBalancesByClientId(clientId);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getClientId().equals(clientId) &&
                        !response.getBalancesCreditCard().isEmpty() &&
                        !response.getBalancesAccount().isEmpty() &&
                        !response.getBalancesCredit().isEmpty())
                .verifyComplete();
    }

    @Test
    public void testGetBalancesByClientIdErrorHandling() {
        String clientId = "client123";

        when(creditCardService.getCreditCardBalancesByClientId(clientId))
                .thenReturn(Flux.error(new RuntimeException("Error fetching credit cards")));
        when(accountService.getAccountBalancesByClientId(clientId))
                .thenReturn(Flux.error(new RuntimeException("Error fetching accounts")));
        when(creditService.getCreditBalancesByClientId(clientId))
                .thenReturn(Flux.error(new RuntimeException("Error fetching credits")));

        Mono<BalanceResponse> result = reportService.getBalancesByClientId(clientId);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getClientId().equals(clientId) &&
                        response.getBalancesCreditCard().isEmpty() &&
                        response.getBalancesAccount().isEmpty() &&
                        response.getBalancesCredit().isEmpty())
                .verifyComplete();
    }




    @Test
    public void testGetClientSummarySuccess() {
        String clientId = "client123";
        when(creditService.getCreditCardClientId(clientId)).thenReturn(Flux.just(new Credit()));
        when(creditCardService.getCreditCardClientId(clientId)).thenReturn(Flux.just(new CreditCard()));
        when(accountService.getAccountClientId(clientId)).thenReturn(Flux.just(new Account()));

        Mono<Map<String, Object>> result = reportService.getClientSummary(clientId);

        StepVerifier.create(result)
                .expectNextMatches(summary -> summary.containsKey("debitCards") && summary.containsKey("credits") && summary.containsKey("creditCards") && summary.containsKey("accounts"))
                .verifyComplete();
    }

    @Test
    public void testGetClientSummaryError() {
        String clientId = "client123";
        when(creditService.getCreditCardClientId(clientId)).thenReturn(Flux.error(new RuntimeException("Error")));
        when(creditCardService.getCreditCardClientId(clientId)).thenReturn(Flux.error(new RuntimeException("Error")));
        when(accountService.getAccountClientId(clientId)).thenReturn(Flux.error(new RuntimeException("Error")));

        Mono<Map<String, Object>> result = reportService.getClientSummary(clientId);

        StepVerifier.create(result)
                .expectError(Exception.class)
                .verify();
    }

    @Test
    public void testGetCommissionByProductIdSuccess() {
        String productId = "product123";
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        Commission commission = new Commission();
        commission.setId("123");
        commission.setDate(new Date());
        commission.setAmount(23.2);
        commission.setDescription("prueba");
        commission.setAccountId("341q23");
        when(accountService.getCommissionsByAccountIdAndDateRange(productId, startDate, endDate))
                .thenReturn(Flux.just(commission));

        Mono<ComissionResponse> result = reportService.getCommissionByProductId(productId, startDate, endDate);

        StepVerifier.create(result)
                .expectNextMatches(response -> response != null)
                .verifyComplete();
    }


    @Test
    public void testGetCommissionByProductIdError() {
        String productId = "product123";
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        when(accountService.getCommissionsByAccountIdAndDateRange(productId, startDate, endDate))
                .thenReturn(Flux.error(new RuntimeException("Error fetching commissions")));

        Mono<ComissionResponse> result = reportService.getCommissionByProductId(productId, startDate, endDate);

        StepVerifier.create(result)
                .expectError(Exception.class)
                .verify();
    }
}
