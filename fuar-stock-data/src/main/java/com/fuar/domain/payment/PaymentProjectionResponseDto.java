package com.fuar.domain.payment;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about the payment projection response dto .")
public interface PaymentProjectionResponseDto {

    @ApiModelProperty(notes = "Amount")
    Double getAmount();

    @ApiModelProperty(notes = "Money type")
    String getMoneyType();

    @ApiModelProperty(notes = "Customer id")
    Long getCustomerId();

    @ApiModelProperty(notes = "Customer name")
    String getName();

    @ApiModelProperty(notes = "Customer surname")
    String getSurname();
}