package com.fuar.api.invoice;

import com.fuar.domain.invoice.Invoice;
import com.fuar.model.invoice.InvoiceResponseDto;
import com.fuar.model.invoice.InvoiceSaveRequestDto;
import com.fuar.model.invoice.InvoiceSearchRequestDto;
import com.fuar.service.invoice.InvoiceService;
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
@RequestMapping("/invoice")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the invoice api.")
public class InvoiceApi {
    Logger logger = LoggerFactory.getLogger(InvoiceApi.class);
    private final InvoiceService invoiceService;

    @GetMapping
    @ApiOperation(value = "Retrieve all invoices")
    public List<InvoiceResponseDto> fetchAllInvoices() {
        return invoiceService.fetchAllInvoices();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Retrieve invoices by parameters")
    public List<InvoiceResponseDto> fetchInvoicesByParameters(@Valid InvoiceSearchRequestDto invoiceSearchRequestDto) {
        return invoiceService.fetchInvoicesByParameters(invoiceSearchRequestDto);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new invoice")
    public Invoice saveInvoice(@RequestBody InvoiceSaveRequestDto item) {
        Invoice invoice = invoiceService.saveInvoice(item);
        return null;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update invoice")
    public Invoice updateInvoice(@RequestBody InvoiceSaveRequestDto item) {
        invoiceService.updateInvoice(item);
        return null;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete invoice")
    public void deleteInvoice(@RequestBody InvoiceSaveRequestDto item) {
        invoiceService.delete(item.getId());
    }

    @PostMapping("/exportExcel")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export invoice excel")
    public Object excelInvoice(@Valid InvoiceSearchRequestDto invoiceSearchRequestDto) {
        ByteArrayResource resource = invoiceService.excelInvoice(invoiceSearchRequestDto);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
        //return resource;
    }

}
