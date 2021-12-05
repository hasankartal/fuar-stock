package com.fuar.model.invoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@Data
@ApiModel(description="All details about the invoice request dto.")
public class InvoiceSaveRequestDto {

    @ApiModelProperty(notes="Id is unique invoice property.")
    private Long id;

    @ApiModelProperty(notes="Id is unique invoice property.")
    private Long invoiceId;

    @NotNull
    @ApiModelProperty(notes="Amount is invoice fee.")
    private Float amount;

    @ApiModelProperty(notes="Customer's id.")
    private Long customerId;

    @NotNull
    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="Date is invoice date")
    private Date orderDate;
}
