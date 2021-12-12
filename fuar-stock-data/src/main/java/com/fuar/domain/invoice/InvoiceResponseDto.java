package com.fuar.domain.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@Data
@ApiModel(description="All details about the invoice response dto .")
public class InvoiceResponseDto {

    @ApiModelProperty(notes="Id is unique invoice property.")
    private Long id;

    @ApiModelProperty(notes="Id is unique invoice id property.")
    private Long invoiceId;

    @ApiModelProperty(notes="Amount is invoice fee.")
    private Float amount;

    @NotNull
    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="Date is invoice date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date orderDate;

    @NotNull
    @ApiModelProperty(notes="Customer who buys product.")
    private String customerName;

    @NotNull
    @ApiModelProperty(notes="Customer id who buys product.")
    private Long customerId;
}
