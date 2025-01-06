package com.nttdata.report.controller;

import com.nttdata.report.model.response.BalanceResponse;
import com.nttdata.report.model.response.ComissionResponse;
import com.nttdata.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/balances/{clientId}")
    public Mono<BalanceResponse> getBalancesByClientId(@PathVariable("clientId") String clientId) {
        return reportService.getBalancesByClientId(clientId);
    }


    @GetMapping("/{id_account}/commissions")
    public Mono<ComissionResponse> getCommissionsByAccountIdAndDateRange(
            @PathVariable("id_account") String accountId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Received request for accountId: {}, startDate: {}, endDate: {}", accountId, startDate, endDate);
        return reportService.getCommissionByProductId(accountId, startDate, endDate);
    }
    @GetMapping("/client-summary/{clientId}")
    public Mono<Map<String, Object>> getClientSummary(@PathVariable String clientId) {
        return reportService.getClientSummary(clientId);
    }
}
