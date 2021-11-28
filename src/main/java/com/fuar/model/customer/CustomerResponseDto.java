package com.fuar.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

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

//    @ApiModelProperty(notes="Date is invoice date")
    private String address;
}
