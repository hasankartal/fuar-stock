package com.fuar.model.country;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@ApiModel(description="All details about the country response dto .")
public class CountryResponseDto {

    @ApiModelProperty(notes="Id is unique country property.")
    private Long id;

    @NotNull
    @ApiModelProperty(notes="Countries's name.")
    private String name;

    @NotNull
    @ApiModelProperty(notes="Countries's code.")
    private String code;
}
