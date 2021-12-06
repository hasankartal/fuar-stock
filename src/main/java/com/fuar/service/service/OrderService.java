package com.fuar.service.service;

import com.fuar.domain.customer.Customer;
import com.fuar.domain.order.Order;
import com.fuar.domain.sale.Sale;
import com.fuar.model.order.OrderResponseDto;
import com.fuar.model.order.OrderSaveRequestDto;
import com.fuar.repository.order.OrderRepository;
import com.fuar.service.customer.CustomerService;
import com.fuar.service.sale.SaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Float amount = 0f;
        return OrderResponseDto.builder()
                .id(item.getId())
                .customerName(item.getCustomer().getName() + " " + item.getCustomer().getSurname())
                .customerId(item.getCustomer().getId())
                .saleId(item.getSale().getId())
                .amount(amount)
                .build();
    }

    @Transactional
    public Order saveOrder(OrderSaveRequestDto request) {
        Customer customer = customerService.findById(request.getCustomerId());
        Sale sale = saleService.findById(request.getSaleId());

        Order order = Order.builder()
                .amount(request.getAmount())
                .customer(customer)
                .sale(sale)
                .build();

        Order savedOrder = orderRepository.save(order);
        //saleEsService.saveNewSaleEs(sale);
        return savedOrder;
    }


}
