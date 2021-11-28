package com.fuar.model.sale;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SaleSearchRequestDto {

    @ApiModelProperty(notes="Amount is invoice fee.")
    private Float amount;

    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="Date is invoice date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date orderDate = new Date();
}
