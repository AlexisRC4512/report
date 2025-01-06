package com.nttdata.report.service;

import com.nttdata.report.model.response.BalanceResponse;
import com.nttdata.report.model.response.ComissionResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

public interface ReportService {
    public Mono<BalanceResponse>getBalancesByClientId(String clientId);
    public Mono<ComissionResponse>getCommissionByProductId(String productId, LocalDate startDate, LocalDate endDate);
    public Mono<Map<String, Object>> getClientSummary(String clientId);
}
