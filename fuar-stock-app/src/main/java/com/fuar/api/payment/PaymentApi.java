package com.fuar.api.payment;

import com.fuar.domain.payment.PaymentResponseDto;
import com.fuar.domain.payment.PaymentSaveRequestDto;
import com.fuar.domain.payment.PaymentSearchRequestDto;
import com.fuar.entity.payment.Payment;
import com.fuar.service.payment.PaymentService;
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
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the payment api.")
public class PaymentApi {

    Logger logger = LoggerFactory.getLogger(PaymentApi.class);
    private final PaymentService paymentService;

    @GetMapping
    @ApiOperation(value = "Retrieve all payments")
    public List<PaymentResponseDto> fetchAllPayments() {
        return paymentService.fetchAllPayments();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Retrieve payments by parameters")
    public List<PaymentResponseDto> fetchPaymentsByParameters(@Valid PaymentSearchRequestDto paymentSearchRequestDto) {
     //   return paymentService.findByMoneyType(paymentSearchRequestDto.getMoneyType());
        return null;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new sale")
    public Payment savePayment(@RequestBody PaymentSaveRequestDto item) {
        Payment payment = paymentService.savePayment(item);
//        saleEsService.saveNewSale(sale).subscribe(result -> logger.info("Entity has been saved: {}", result));
        return null;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update payment")
    public Payment updatePayment(@RequestBody PaymentSaveRequestDto item) {
        paymentService.updatePayment(item);
        return null;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete payment")
    public void deletePayment(@RequestBody PaymentSaveRequestDto item) {
        paymentService.delete(item.getId());
    }

    @PostMapping("/exportExcel")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export payment excel")
    public Object excelPayment() {
        ByteArrayResource resource = paymentService.excelPayment(null);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
        //return resource;
    }

    @GetMapping("/exportExcelByParameters")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export payment excel with parameter")
    public Object excelPaymentByParameters(@RequestParam String moneyType) {
        ByteArrayResource resource = paymentService.excelPayment(moneyType);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
    }
}
