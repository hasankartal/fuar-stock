package com.fuar.service.payment;

import com.fuar.domain.payment.PaymentProjectionResponseDto;
import com.fuar.domain.payment.PaymentResponseDto;
import com.fuar.domain.payment.PaymentSaveRequestDto;
import com.fuar.domain.payment.PaymentSearchRequestDto;
import com.fuar.entity.customer.Customer;
import com.fuar.entity.payment.Payment;
import com.fuar.repository.customer.CustomerRepository;
import com.fuar.repository.payment.PaymentRepository;
import com.fuar.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerService customerService;

    public List<PaymentResponseDto> fetchAllPayments() {
        List<Payment> paymentList = findAll();
        List<PaymentResponseDto> paymentResponseDtoList = new ArrayList<>();
        for (Payment payment : paymentList) {
            PaymentResponseDto paymentResponseDto = mapToDto(payment);
            paymentResponseDtoList.add(paymentResponseDto);
        }
        return paymentResponseDtoList;
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
//        return paymentRepository.findAll(sortByOrderDateDesc());
    }

    public Payment findById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        return payment.get();
    }

/*    private Sort sortByOrderDateDesc() {
        return Sort.by(Sort.Direction.DESC, "orderDate");
    }
*/
    private PaymentResponseDto mapToDto(Payment item) {
        if (item == null) {
            return null;
        }
//        Locale locale = new Locale("tr", "TR");
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
//        String date = dateFormat.format(item.getOrderDate());
        // String date = item.getOrderDate().toLocaleString();
        return PaymentResponseDto.builder()
                .id(item.getId())
                .customerName(item.getCustomer().getName() + " " + item.getCustomer().getSurname())
                .customerId(item.getCustomer().getId())
                .amount(item.getAmount())
                .moneyType(item.getMoneyType())
                .build();
    }

    public List<PaymentResponseDto> fetchPaymentsByParameters(PaymentSearchRequestDto paymentSearchRequestDto) {
        List<Payment> paymentList = findAll();
        paymentList = paymentList.stream()
                // .filter(row -> row.getSale().getId().equals(orderSearchRequestDto.getSaleId() != null ? orderSearchRequestDto.getSaleId() : ""))
                .collect(Collectors.toList());

        List<PaymentResponseDto> paymentResponseDtoList = new ArrayList<>();
        for (Payment payment : paymentList) {
            PaymentResponseDto paymentResponseDto = mapToDto(payment);
            paymentResponseDtoList.add(paymentResponseDto);
        }
        return paymentResponseDtoList;
    }

    public List<PaymentProjectionResponseDto> sumAmountByCustomer() {
        List<PaymentProjectionResponseDto> paymentList = paymentRepository.sumAmountByCustomer();

        return paymentList;
    }

    @Transactional
    public Payment savePayment(PaymentSaveRequestDto request) {
        Customer customer = customerService.findById(request.getCustomerId());

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .customer(customer)
                .moneyType(request.getMoneyType())
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        //saleEsService.saveNewSaleEs(sale);
        return savedPayment;
    }

    @Transactional
    public Payment updatePayment(PaymentSaveRequestDto request) {
        Payment payment = paymentRepository.findById(request.getId()).orElse(null);

        payment.setId(request.getId());
        payment.setAmount(request.getAmount());
        payment.setMoneyType(request.getMoneyType());
        payment.setOperation("UPDATED");

        Payment updatedPayment = paymentRepository.save(payment);

        return updatedPayment;
    }

    @Transactional
    public void delete(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if(payment != null) {
            paymentRepository.deleteById(id);
        }
    }

    public ByteArrayResource excelPayment(String moneyType) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        arrangeHeader(sheet);

        List<PaymentResponseDto> paymentResponseDtoList = null;

        if (moneyType == null) {
            paymentResponseDtoList = fetchAllPayments();
        } else {
        //    paymentResponseDtoList = findByMoneyType(moneyType);
        }

        int numberOfRow = 1;
        for (PaymentResponseDto paymentResponseDto: paymentResponseDtoList) {
            createNewRow(sheet, paymentResponseDto, numberOfRow);
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

        Cell amount = header.createCell(headerCellNumber);
        amount.setCellValue("Tutar");
        headerCellNumber++;

        Cell currencyType = header.createCell(headerCellNumber);
        currencyType.setCellValue("Para Birimi");
        headerCellNumber++;

        Cell date = header.createCell(headerCellNumber);
        date.setCellValue("Tarih");
        headerCellNumber++;

        return header;
    }

    private void createNewRow(Sheet sheet, PaymentResponseDto paymentResponseDto, int numberOfRow) {
        Row row = sheet.createRow(numberOfRow);
        int numberOfColumn = 0;
        Cell idCell = row.createCell(numberOfColumn);
        idCell.setCellValue(paymentResponseDto.getId());
        numberOfColumn++;

        Cell amountCell = row.createCell(numberOfColumn);
        amountCell.setCellValue(paymentResponseDto.getAmount() != null ? paymentResponseDto.getAmount().toString() : "");
        numberOfColumn++;

        Cell moneyTypeCell = row.createCell(numberOfColumn);
        moneyTypeCell.setCellValue(paymentResponseDto.getMoneyType());
        numberOfColumn++;

        Cell orderDateCell = row.createCell(numberOfColumn);
        Locale locale = new Locale("tr", "TR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(paymentResponseDto.getPaymentDate());
        orderDateCell.setCellValue(date);
        numberOfColumn++;
    }
}
