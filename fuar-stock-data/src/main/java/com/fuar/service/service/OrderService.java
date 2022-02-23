package com.fuar.service.service;

import com.fuar.domain.payment.PaymentProjectionResponseDto;
import com.fuar.entity.customer.Customer;
import com.fuar.entity.order.Order;
import com.fuar.entity.sale.Sale;
import com.fuar.domain.order.OrderResponseDto;
import com.fuar.domain.order.OrderSaveRequestDto;
import com.fuar.domain.order.OrderSearchRequestDto;
import com.fuar.repository.order.OrderRepository;
import com.fuar.service.customer.CustomerService;
import com.fuar.service.sale.SaleService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderResponseDto> fetchAllOrders() {
        List<Order> orderList = findAll();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderResponseDto orderResponseDto = mapToDto(order);
            orderResponseDtoList.add(orderResponseDto);
        }
        return orderResponseDtoList;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    private OrderResponseDto mapToDto(Order item) {
        if (item == null) {
            return null;
        }
//        Locale locale = new Locale("tr", "TR");
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
//        String date = dateFormat.format(item.getOrderDate());
        // String date = item.getOrderDate().toLocaleString();
        return OrderResponseDto.builder()
                .id(item.getId())
                .customerName(item.getCustomer().getName() + " " + item.getCustomer().getSurname())
                .customerId(item.getCustomer().getId())
                .amount(item.getAmount())
                .moneyType(item.getMoneyType())
                .build();
    }

    public List<OrderResponseDto> fetchOrdersByParameters(OrderSearchRequestDto orderSearchRequestDto) {
        List<Order> orderList = findAll();
        orderList = orderList.stream()
               // .filter(row -> row.getSale().getId().equals(orderSearchRequestDto.getSaleId() != null ? orderSearchRequestDto.getSaleId() : ""))
                .collect(Collectors.toList());

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderResponseDto orderResponseDto = mapToDto(order);
            orderResponseDtoList.add(orderResponseDto);
        }
        return orderResponseDtoList;
    }

    public List<PaymentProjectionResponseDto> sumAmountByCustomer() {
        List<PaymentProjectionResponseDto> paymentList = orderRepository.sumAmountByCustomer();

        return paymentList;
    }

    @Transactional
    public Order saveOrder(OrderSaveRequestDto request) {
        Customer customer = customerService.findById(request.getCustomerId());

        Order order = Order.builder()
                .amount(request.getAmount())
                .moneyType(request.getMoneyType())
                .orderDate(request.getOrderDate())
                .customer(customer)
                .build();

        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    @Transactional
    public Order updateOrder(OrderSaveRequestDto request) {
        Customer customer = customerService.findById(request.getCustomerId());

        Order order = orderRepository.findById(request.getId()).orElse(null);
        order.setId(request.getId());
        order.setAmount(request.getAmount());
        order.setMoneyType(request.getMoneyType());
        order.setCustomer(customer);
        order.setOrderDate(request.getOrderDate());

        Order updatedOrder = orderRepository.save(order);
        return updatedOrder;
    }

    public void delete(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order != null) {
            orderRepository.deleteById(id);
        }
    }

    public ByteArrayResource excelOrder(OrderSearchRequestDto orderSearchRequestDto) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        arrangeHeader(sheet);

        List<OrderResponseDto> orderResponseDtoList = fetchOrdersByParameters(orderSearchRequestDto);

        int numberOfRow = 1;
        for (OrderResponseDto orderResponseDto : orderResponseDtoList) {
            createNewRow(sheet, orderResponseDto, numberOfRow);
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

        Cell saleId = header.createCell(headerCellNumber);
        saleId.setCellValue("Satış Numarası");
        headerCellNumber++;

        Cell customer = header.createCell(headerCellNumber);
        customer.setCellValue("Müşteri");
        headerCellNumber++;

        Cell customerId = header.createCell(headerCellNumber);
        customerId.setCellValue("Müşteri Id");
        headerCellNumber++;

        Cell amount = header.createCell(headerCellNumber);
        amount.setCellValue("Tutar");
        headerCellNumber++;

        Cell moneyType = header.createCell(headerCellNumber);
        moneyType.setCellValue("Para Birimi");
        headerCellNumber++;

        Cell date = header.createCell(headerCellNumber);
        date.setCellValue("Tarih");
        headerCellNumber++;

        return header;
    }

    private void createNewRow(Sheet sheet, OrderResponseDto orderResponseDto, int numberOfRow) {
        Row row = sheet.createRow(numberOfRow);
        int numberOfColumn = 0;
        Cell idCell = row.createCell(numberOfColumn);
        idCell.setCellValue(orderResponseDto.getId());
        numberOfColumn++;

        Cell saleIdCell = row.createCell(numberOfColumn);
        saleIdCell.setCellValue(orderResponseDto.getSaleId() != null ? orderResponseDto.getSaleId().toString() : "");
        numberOfColumn++;

        Cell customerCell = row.createCell(numberOfColumn);
        customerCell.setCellValue(orderResponseDto.getCustomerName());
        numberOfColumn++;

        Cell customerIdCell = row.createCell(numberOfColumn);
        customerIdCell.setCellValue(orderResponseDto.getCustomerId());
        numberOfColumn++;

        Cell amountCell = row.createCell(numberOfColumn);
        amountCell.setCellValue(orderResponseDto.getAmount());
        numberOfColumn++;

        Cell moneyTypeCell = row.createCell(numberOfColumn);
        moneyTypeCell.setCellValue(orderResponseDto.getMoneyType());
        numberOfColumn++;

        Cell orderDateCell = row.createCell(numberOfColumn);
        Locale locale = new Locale("tr", "TR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        if (orderResponseDto.getOrderDate() != null ) {
            String date = dateFormat.format(orderResponseDto.getOrderDate());
            orderDateCell.setCellValue(date);
        }

        numberOfColumn++;
    }
}
