package com.fuar.domain.claim;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description="All details about the claim response dto .")
public class ClaimResponseDto {

    @ApiModelProperty(notes="Amount")
    private Double amount;

    @ApiModelProperty(notes="Collection money type")
    private String moneyType;

    @ApiModelProperty(notes="Collection customer id")
    private Long customerId;

    @ApiModelProperty(notes="Collection customer")
    private String customer;

    public ClaimResponseDto() {

    }

    public ClaimResponseDto(String moneyType, Long customerId, String customer) {
        this.moneyType = moneyType;
        this.customerId = customerId;
        this.customer = customer;
    }

    public String keyOfClaim() {
        return getCustomerId() + "-" + getMoneyType();
    }
}
