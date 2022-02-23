package com.fuar.domain.collection;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@ApiModel(description="All details about the collection save request dto .")
public class CollectionSaveRequestDto {

    private Long id;
    private Float amount;
    private String moneyType;
    private Long customerId;
    private String customer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date paymentDate;
}
