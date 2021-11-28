package com.fuar.api.customer;

import com.fuar.api.sale.SaleApi;
import com.fuar.domain.customer.Customer;
import com.fuar.domain.sale.Sale;
import com.fuar.model.customer.CustomerResponseDto;
import com.fuar.model.customer.CustomerSaveRequestDto;
import com.fuar.service.customer.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the customer api.")
public class CustomerApi {

    Logger logger = LoggerFactory.getLogger(CustomerApi.class);
    private final CustomerService customerService;

    @GetMapping
    @ApiOperation(value = "Retrieve all customers")
    public List<CustomerResponseDto> fetchAllCustomers() {
        return customerService.fetchAllCustomers();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new sale")
    public Sale saveCustomer(@RequestBody CustomerSaveRequestDto item) {
        Customer customer = customerService.save(item);

        return null;
    }
}
