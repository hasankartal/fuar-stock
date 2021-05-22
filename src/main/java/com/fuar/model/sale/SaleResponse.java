package com.fuar.model.sale;

import com.fuar.common.MoneyType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
public class SaleResponse {

    private Long id;
    private Float amount;
    private String moneyType;
    private String orderDate;

}
