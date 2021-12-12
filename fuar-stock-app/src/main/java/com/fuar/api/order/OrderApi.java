package com.fuar.api.order;

import com.fuar.entity.invoice.Invoice;
import com.fuar.entity.order.Order;
import com.fuar.domain.invoice.InvoiceSaveRequestDto;
import com.fuar.domain.invoice.InvoiceSearchRequestDto;
import com.fuar.domain.order.OrderResponseDto;
import com.fuar.domain.order.OrderSaveRequestDto;
import com.fuar.domain.order.OrderSearchRequestDto;
import com.fuar.service.service.OrderService;
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
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the order api.")
public class OrderApi {
    Logger logger = LoggerFactory.getLogger(OrderApi.class);
    private final OrderService orderService;

    @GetMapping
    @ApiOperation(value = "Retrieve all orders")
    public List<OrderResponseDto> fetchAllOrders() {
        return orderService.fetchAllOrders();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Retrieve orders by parameters")
    public List<OrderResponseDto> fetchSalesByParameters(@Valid OrderSearchRequestDto orderSearchRequestDto) {
        return orderService.fetchOrdersByParameters(orderSearchRequestDto);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new order")
    public Order saveInvoice(@RequestBody OrderSaveRequestDto item) {
        Order order = orderService.saveOrder(item);
        return null;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update order")
    public Order updateOrder(@RequestBody OrderSaveRequestDto item) {
        orderService.updateOrder(item);
        return null;
    }

    @PostMapping("/exportExcel")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export order excel")
    public Object excelInvoice(@Valid OrderSearchRequestDto orderSearchRequestDto) {
        ByteArrayResource resource = orderService.excelOrder(orderSearchRequestDto);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
        //return resource;
    }
}
