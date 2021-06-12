package com.fuar.api.sale;

import com.fuar.domain.sale.Sale;
import com.fuar.model.sale.SaleResponseDto;
import com.fuar.model.sale.SaleSaveRequestDto;
import com.fuar.service.sale.es.SaleEsService;
import com.fuar.service.sale.SaleService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the sale api.")
public class SaleApi {
    Logger logger = LoggerFactory.getLogger(SaleApi.class);
    private final SaleService saleService;
    private final SaleEsService saleEsService;

    @GetMapping
    @ApiOperation(value = "Retrieve all sales")
    public Flux<SaleResponseDto> getAllSales() {
        return saleService.getAll();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new sale")
    public Mono saveSale(@RequestBody SaleSaveRequestDto item) {
        Sale sale = Sale.builder()
                .amount(item.getAmount())
                .money(item.getMoneyType())
                .orderDate(new Date())
                .build();
        saleService.saveMono(item)
                .subscribe(result -> logger.info("Entity has been saved: {}", result));
//        saleEsService.saveNewSale(sale).subscribe(result -> logger.info("Entity has been saved: {}", result));
        return null;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete sale")
    public void deleteSale(@RequestBody SaleSaveRequestDto item) {
        saleService.delete(item.getId());
    }

    @PostMapping("/excelSale")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get sale excel")
    public Object excelSale() {
        ByteArrayResource resource = saleEsService.excelSale();

//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE))
        return ResponseEntity.ok().contentType(new MediaType("application", "vnd.ms-excel"))
            .body(resource);
        //return resource;
    }

    @GetMapping("/excelSales")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get sale excel")
    public Object excelSales() {
        ByteArrayResource resource = saleEsService.excelSale();

//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE))
        return ResponseEntity.ok().contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
        //return resource;
    }
}
