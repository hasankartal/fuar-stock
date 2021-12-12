package com.fuar.api.customer;

import com.fuar.entity.country.Country;
import com.fuar.entity.customer.Customer;
import com.fuar.entity.sale.Sale;
import com.fuar.domain.country.CountryResponseDto;
import com.fuar.domain.country.CountrySaveRequestDto;
import com.fuar.domain.country.CountrySearchRequestDto;
import com.fuar.domain.customer.CustomerResponseDto;
import com.fuar.domain.customer.CustomerSaveRequestDto;
import com.fuar.domain.customer.CustomerSearchRequestDto;
import com.fuar.service.customer.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/search")
    @ApiOperation(value = "Retrieve countries by parameters")
    public List<CustomerResponseDto> fetchCustomerByParameters(@Valid CustomerSearchRequestDto customerSearchRequestDto) {
        return customerService.fetchCustomersByParameters(customerSearchRequestDto);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new customer")
    public Customer saveCustomer(@RequestBody CustomerSaveRequestDto item) {
        Customer customer = customerService.save(item);

        return customer;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update customer")
    public Customer updateCustomer(@RequestBody CustomerSaveRequestDto item) {
        customerService.updateCustomer(item);
        return null;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete customer")
    public void deleteCustomer(@RequestBody CustomerSaveRequestDto item) {
        customerService.delete(item.getId());
    }

    @GetMapping("/exportExcel")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export customer excel")
    public Object excelCustomer(@Valid CustomerSearchRequestDto customerSearchRequestDto) {
        ByteArrayResource resource = customerService.excelCustomer(customerSearchRequestDto);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
        //return resource;
    }

}
