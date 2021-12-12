package com.fuar.domain.country;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@ApiModel(description="All details about the country save request dto.")
public class CountrySaveRequestDto {

    @ApiModelProperty(notes="Id is unique country property.")
    private Long id;

    @NotNull
    @ApiModelProperty(notes="Country's name.")
    private String name;

    @NotNull
    @ApiModelProperty(notes="Country's code.")
    private String code;
}
