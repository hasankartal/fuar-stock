package com.fuar.domain.undischarge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="All details about the claim response dto .")
public class UndischargeResponseDto {

    @ApiModelProperty(notes = "Amount")
    private Double amount;

    @ApiModelProperty(notes = "Collection money type")
    private String moneyType;

    @ApiModelProperty(notes = "Collection customer id")
    private Long customerId;

    @ApiModelProperty(notes = "Collection customer")
    private String customer;

    public UndischargeResponseDto() {

    }

    public UndischargeResponseDto(String moneyType, Long customerId, String customer) {
        this.moneyType = moneyType;
        this.customerId = customerId;
        this.customer = customer;
    }

    public String keyOfClaim() {
        return getCustomerId() + "-" + getMoneyType();
    }

}