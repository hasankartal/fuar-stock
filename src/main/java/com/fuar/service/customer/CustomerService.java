package com.fuar.service.customer;

import com.fuar.domain.customer.Customer;
import com.fuar.model.customer.CustomerRequestDto;
import com.fuar.model.customer.CustomerResponseDto;
import com.fuar.model.customer.CustomerSaveRequestDto;
import com.fuar.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Customer save(CustomerSaveRequestDto request) {
        return  repository.save(Customer.
                builder()
                .name(request.getName())
                .surname(request.getSurname())
                .build());
    }

    public List<CustomerResponseDto> fetchAllCustomers() {
        List<Customer> customerList = findAll();
        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerResponseDto customerResponseDto = mapToDto(customer);
            customerResponseDtoList.add(customerResponseDto);
        }
        return customerResponseDtoList;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    private CustomerResponseDto mapToDto(Customer item) {
        if (item == null) {
            return null;
        }
//        Locale locale = new Locale("tr", "TR");
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
//        String date = dateFormat.format(item.getOrderDate());
        // String date = item.getOrderDate().toLocaleString();
        return CustomerResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .surname(item.getSurname())
                .build();
    }

    private Sort sortByOrderDateDesc() {
        return Sort.by(Sort.Direction.DESC, "orderDate");
    }
}
