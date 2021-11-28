package com.fuar.model.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fuar.common.MoneyType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@ApiModel(description="All details about the sale response dto .")
public class SaleResponseDto {
    @ApiModelProperty(notes="Id is unique sale property.")
    private Long id;

    @ApiModelProperty(notes="Amount is invoice fee.")
    private Float amount;

    @NotNull
    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="Date is invoice date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date orderDate;

    @NotNull
    @ApiModelProperty(notes="Customer who buys product.")
    private String customer;
}
