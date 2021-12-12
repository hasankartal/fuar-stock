package com.fuar.domain.country;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CountrySearchRequestDto {

    @ApiModelProperty(notes="Country code")
    private String code;

    @ApiModelProperty(notes="Country name")
    private String name;
}
