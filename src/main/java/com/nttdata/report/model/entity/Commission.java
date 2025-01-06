package com.nttdata.report.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commission {
    private String id;
    private String accountId;
    private String clientId;
    private double amount;
    private Date date;
    private String description;
}
