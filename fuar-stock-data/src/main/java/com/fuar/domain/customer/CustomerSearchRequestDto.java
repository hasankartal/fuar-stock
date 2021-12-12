package com.fuar.domain.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerSearchRequestDto {

    @ApiModelProperty(notes="Customer name")
    private String name;

    @ApiModelProperty(notes="Customer surname")
    private String surname;

    @ApiModelProperty(notes="Customer address")
    private String address;

}