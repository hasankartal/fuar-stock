package com.fuar.domain.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@Data
@ApiModel(description="All details about the order response dto .")
public class OrderResponseDto {

    @ApiModelProperty(notes="Id is unique order property.")
    private Long id;

    @ApiModelProperty(notes="Amount is order fee.")
    private Float amount;

    @NotNull
    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="Date is invoice date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date orderDate;

    @ApiModelProperty(notes="Id is unique sale property.")
    private Long saleId;

    @NotNull
    @ApiModelProperty(notes="Customer who buys product.")
    private String customerName;

    @NotNull
    @ApiModelProperty(notes="Customer id who buys product.")
    private Long customerId;
}
