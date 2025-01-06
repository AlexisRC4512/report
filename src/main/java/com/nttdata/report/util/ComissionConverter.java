package com.nttdata.report.util;

import com.nttdata.report.model.entity.Commission;
import com.nttdata.report.model.response.ComissionResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public class ComissionConverter {
    public static Mono<ComissionResponse> toComissionResponse(List<Commission>commissionList) {
        ComissionResponse comissionResponse = new ComissionResponse();
        comissionResponse.setBalancesAccount(commissionList);
        return Mono.just(comissionResponse);
    }
}
