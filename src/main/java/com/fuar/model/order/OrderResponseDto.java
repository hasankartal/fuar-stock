package com.fuar.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@ApiModel(description="All details about the order response dto .")
public class OrderResponseDto {

    @ApiModelProperty(notes="Id is unique order property.")
    private Long id;

    @ApiModelProperty(notes="Amount is order fee.")
    private Float amount;

    @ApiModelProperty(notes="Id is unique sale property.")
    private Long saleId;

    @NotNull
    @ApiModelProperty(notes="Customer who buys product.")
    private String customerName;

    @NotNull
    @ApiModelProperty(notes="Customer id who buys product.")
    private Long customerId;
}
