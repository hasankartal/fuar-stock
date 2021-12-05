package com.fuar.service.customer;

import com.fuar.domain.country.Country;
import com.fuar.domain.customer.Customer;
import com.fuar.model.country.CountryResponseDto;
import com.fuar.model.country.CountrySaveRequestDto;
import com.fuar.model.customer.CustomerRequestDto;
import com.fuar.model.customer.CustomerResponseDto;
import com.fuar.model.customer.CustomerSaveRequestDto;
import com.fuar.model.customer.CustomerSearchRequestDto;
import com.fuar.repository.customer.CustomerRepository;
import com.fuar.service.country.CountryService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CountryService countryService;

    public Customer save(CustomerSaveRequestDto request) {
        Country country= countryService.findById(request.getCountryId());
        return  repository.save(Customer.
                builder()
                .name(request.getName())
                .surname(request.getSurname())
                .address(request.getAddress())
                .country(country)
                .build());
    }

    @Transactional
    public Customer updateCustomer(CustomerSaveRequestDto request) {
        Customer customer = repository.findById(request.getId()).orElse(null);
        Country country= countryService.findById(request.getCountryId());

        customer.setId(request.getId());
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setAddress(request.getAddress());
        customer.setCountry(country);

        Customer updatedCustomer = repository.save(customer);

        return updatedCustomer;
    }

    @Transactional
    public void delete(Long id) {
        Optional<Customer> customer = repository.findById(id);
        if(customer != null) {
            repository.deleteById(id);
        }
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

    public List<CustomerResponseDto> fetchCustomersByParameters(CustomerSearchRequestDto customerSearchRequestDto) {
        List<Customer> customerList = findAll();
        customerList = customerList.stream()
                .filter(row -> row.getName().contains(customerSearchRequestDto.getName() != null ? customerSearchRequestDto.getName() : ""))
                .filter(row -> row.getSurname().contains(customerSearchRequestDto.getSurname() != null ? customerSearchRequestDto.getSurname() : "" ))
                .filter(row -> row.getAddress().contains(customerSearchRequestDto.getAddress() != null ? customerSearchRequestDto.getAddress() : "" ))
                .collect(Collectors.toList());

        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerResponseDto customerResponseDto = mapToDto(customer);
            customerResponseDtoList.add(customerResponseDto);
        }
        return customerResponseDtoList;
    }

    public Customer findById(Long id) {
        Optional<Customer> customer = repository.findById(id);

        return customer.get();
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
                .address(item.getAddress())
                .countryName(item.getCountry().getName())
                .countryId(item.getCountry().getId())
                .build();
    }

    private Sort sortByOrderDateDesc() {
        return Sort.by(Sort.Direction.DESC, "orderDate");
    }

    public ByteArrayResource excelCustomer(CustomerSearchRequestDto customerSearchRequestDto) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        arrangeHeader(sheet);

        List<CustomerResponseDto> customerResponseDtoList = fetchCustomersByParameters(customerSearchRequestDto);

        int numberOfRow = 1;
        for (CustomerResponseDto customerResponseDto : customerResponseDtoList) {
            createNewRow(sheet, customerResponseDto, numberOfRow);
            numberOfRow++;
        }

        ByteArrayOutputStream resource = new ByteArrayOutputStream();
        try {
            workbook.write(resource);
        } catch (IOException io) {
            return null;
        }
        ByteArrayResource response = new ByteArrayResource(resource.toByteArray());
        return response;
    }

    private Row arrangeHeader(Sheet sheet) {
        int headerCellNumber = 0;
        Row header = sheet.createRow(0);

        Cell id = header.createCell(headerCellNumber);
        id.setCellValue("Id");
        headerCellNumber++;

        Cell nameType = header.createCell(headerCellNumber);
        nameType.setCellValue("Ad");
        headerCellNumber++;

        Cell surnameType = header.createCell(headerCellNumber);
        surnameType.setCellValue("Soyad");
        headerCellNumber++;

        Cell address = header.createCell(headerCellNumber);
        address.setCellValue("Adres");
        headerCellNumber++;

        Cell companyType = header.createCell(headerCellNumber);
        companyType.setCellValue("Ülke");
        headerCellNumber++;

        return header;
    }

    private void createNewRow(Sheet sheet, CustomerResponseDto customerResponseDto, int numberOfRow) {
        Row row = sheet.createRow(numberOfRow);
        int numberOfColumn = 0;
        Cell idCell = row.createCell(numberOfColumn);
        idCell.setCellValue(customerResponseDto.getId());
        numberOfColumn++;

        Cell nameCell = row.createCell(numberOfColumn);
        nameCell.setCellValue(customerResponseDto.getName() != null ? customerResponseDto.getName().toString() : "");
        numberOfColumn++;

        Cell surnameCell = row.createCell(numberOfColumn);
        surnameCell.setCellValue(customerResponseDto.getSurname() != null ? customerResponseDto.getSurname().toString() : "");
        numberOfColumn++;

        Cell addressCell = row.createCell(numberOfColumn);
        addressCell.setCellValue(customerResponseDto.getAddress() != null ? customerResponseDto.getAddress().toString() : "");
        numberOfColumn++;

        Cell countryCell = row.createCell(numberOfColumn);
        countryCell.setCellValue(customerResponseDto.getCountryName() != null ? customerResponseDto.getCountryName().toString() : "");
        numberOfColumn++;
    }
}
