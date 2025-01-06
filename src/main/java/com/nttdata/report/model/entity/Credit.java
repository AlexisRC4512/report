package com.nttdata.report.model.entity;


import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Credit {
    private String id;
    private String type;
    private double amount;
    private double interestRate;
    private Date startDate;
    private Date endDate;
    private double outstandingBalance;
    private String clientId;
    private List<Payment> payments;
}
