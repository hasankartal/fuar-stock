package com.fuar.domain.sale;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@Data
@ApiModel(description="All details about the sale request dto.")
public class SaleSaveRequestDto {

    @ApiModelProperty(notes="Id is unique sale property.")
    private Long id;

    @ApiModelProperty(notes="Id is unique invoice property.")
    private Long invoiceId;

    @NotNull
    @ApiModelProperty(notes="Amount is invoice fee.")
    private Float amount;

    @NotNull
    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="Date is invoice date")
    private Date orderDate;
}
