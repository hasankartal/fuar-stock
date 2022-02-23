package com.fuar.domain.collection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about the collection projection response dto .")
public interface CollectionProjectionResponseDto {

    @ApiModelProperty(notes="Collection amount")
    Double getAmount();

    @ApiModelProperty(notes="Collection money type")
    String getMoneyType();

    @ApiModelProperty(notes="Collection customer id")
    Long getCustomerId();

    @ApiModelProperty(notes="Customer name")
    String getName();

    @ApiModelProperty(notes="Customer surname")
    String getSurname();
}
