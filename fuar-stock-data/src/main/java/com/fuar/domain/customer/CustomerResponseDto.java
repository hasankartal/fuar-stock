package com.fuar.domain.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@ApiModel(description="All details about the customer response dto .")
public class CustomerResponseDto {
    @ApiModelProperty(notes="Id is unique sale property.")
    private Long id;

    @NotNull
    @ApiModelProperty(notes="Customer's name.")
    private String name;

    @NotNull
    @ApiModelProperty(notes="Customer's surname.")
    private String surname;

    @ApiModelProperty(notes="Customer's country name.")
    private String countryName;

    @ApiModelProperty(notes="Customer's country id.")
    private Long countryId;

//    @ApiModelProperty(notes="Date is invoice date")
    private String address;

}
