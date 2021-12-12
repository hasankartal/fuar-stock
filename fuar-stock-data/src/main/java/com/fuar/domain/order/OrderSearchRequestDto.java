package com.fuar.domain.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OrderSearchRequestDto {

    @ApiModelProperty(notes="Person who delivers order.")
    private String customer;

    @ApiModelProperty(notes="SaleId is unique invoice property.")
    private Long saleId;

    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="Date is invoice date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date orderDate = new Date();
}
