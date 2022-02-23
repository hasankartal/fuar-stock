package com.fuar.domain.collection;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@ApiModel(description="All details about the collection response dto .")
public class CollectionResponseDto {

    @ApiModelProperty(notes="Collection id")
    private Long id;

    @ApiModelProperty(notes="Collection amount")
    private Float amount;

    @ApiModelProperty(notes="Collection money type")
    private String moneyType;

    @ApiModelProperty(notes="Collection customer id")
    private Long customerId;

    @ApiModelProperty(notes="Collection customer")
    private String customer;

    @ApiModelProperty(notes="Collection payment date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date paymentDate;
}