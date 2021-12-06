package com.fuar.model.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@ApiModel(description="All details about the order request dto.")
public class OrderSaveRequestDto {

    @ApiModelProperty(notes="Id is unique order property.")
    private Long id;

    @NotNull
    @ApiModelProperty(notes="Amount is order fee.")
    private Float amount;

    @ApiModelProperty(notes="Id is unique sale property.")
    private Long saleId;

    @ApiModelProperty(notes="Customer's id.")
    private Long customerId;
}
