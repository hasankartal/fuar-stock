package com.fuar.domain.invoice;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class InvoiceSearchRequestDto {

    @ApiModelProperty(notes="Amount is invoice fee.")
    private Float amount;

    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="InvoiceId is unique invoice property.")
    private Long invoiceId;

    @ApiModelProperty(notes="Invoice's customer.")
    private String customer;

    @ApiModelProperty(notes="Date is invoice date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date orderDate = new Date();
}
