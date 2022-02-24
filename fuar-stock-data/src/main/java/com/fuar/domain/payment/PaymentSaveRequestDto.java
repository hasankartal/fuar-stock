package com.fuar.domain.payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@ApiModel(description="All details about the payment request dto.")
public class PaymentSaveRequestDto {

    @ApiModelProperty(notes="Id is unique payment property.")
    private Long id;

    @NotNull
    @ApiModelProperty(notes="Amount is payment fee.")
    private Float amount;

    @ApiModelProperty(notes="Customer's id.")
    private Long customerId;

    @NotNull
    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;
}
