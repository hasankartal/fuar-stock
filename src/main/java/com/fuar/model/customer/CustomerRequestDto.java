package com.fuar.model.customer;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@ApiModel(description="All details about the customer request dto .")
public class CustomerRequestDto {

    private Long id;

    private String name;
    private String surname;
}
