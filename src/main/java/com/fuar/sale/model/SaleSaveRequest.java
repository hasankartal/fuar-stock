package com.fuar.sale.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SaleSaveRequest {

    private Long id;
    private Float amount;
}
