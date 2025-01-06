package com.nttdata.report.model.response;

import com.nttdata.report.model.entity.Commission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComissionResponse {
    private List<Commission> balancesAccount;

}
