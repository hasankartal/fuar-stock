package com.fuar.domain.collection;

import com.fuar.entity.customer.Customer;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@ApiModel(description="All details about the collection request dto .")
public class CollectionRequestDto {

    private Float amount;
    private String moneyType;
    private Long customerId;
    private String customer;
}
