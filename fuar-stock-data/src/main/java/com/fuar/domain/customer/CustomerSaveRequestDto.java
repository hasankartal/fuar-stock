package com.fuar.domain.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@ApiModel(description="All details about the customer save request dto.")
public class CustomerSaveRequestDto {

    @ApiModelProperty(notes="Id is unique customer property.")
    private Long id;

    @NotNull
    @ApiModelProperty(notes="Customer's name.")
    private String name;

    @NotNull
    @ApiModelProperty(notes="Customer's surname.")
    private String surname;

    @ApiModelProperty(notes="Customer's country.")
    private Long countryId;

    @ApiModelProperty(notes="Customer's address.")
    private String address;
}
