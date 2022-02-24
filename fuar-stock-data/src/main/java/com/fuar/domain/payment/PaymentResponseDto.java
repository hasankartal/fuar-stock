package com.fuar.domain.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@Data
@ApiModel(description="All details about the payment response dto .")
public class PaymentResponseDto {

    @ApiModelProperty(notes="Id is unique payment property.")
    private Long id;

    @ApiModelProperty(notes="Amount is payment fee.")
    private Float amount;

    @NotNull
    @ApiModelProperty(notes="Money type is currency type which belongs to invoice.")
    private String moneyType;

    @ApiModelProperty(notes="Date is payment date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date paymentDate;

    @ApiModelProperty(notes="Id is unique order property.")
    private Long orderId;

    @NotNull
    @ApiModelProperty(notes="Seller name who gives product")
    private String customerName;

    @NotNull
    @ApiModelProperty(notes="Seller id who gives product.")
    private Long customerId;

}
