package com.fuar.model.sale;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SaleSaveRequest {

    private Long id;
    private Float amount;
    private String moneyType;
    private Date orderDate;

}
