package com.fuar.api.sale;

import com.fuar.entity.sale.Sale;
import com.fuar.domain.sale.SaleResponseDto;
import com.fuar.domain.sale.SaleSaveRequestDto;
import com.fuar.domain.sale.SaleSearchRequestDto;
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

import javax.validation.Valid;
import java.util.List;

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
    public List<SaleResponseDto> fetchAllSales() {
        return saleService.fetchAllSales();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Retrieve sales by parameters")
    public List<SaleResponseDto> fetchSalesByParameters(@Valid SaleSearchRequestDto saleSearchRequestDto) {
        return saleService.findByMoneyType(saleSearchRequestDto.getMoneyType());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new sale")
    public Sale saveSale(@RequestBody SaleSaveRequestDto item) {
        Sale sale = saleService.saveSale(item);
//        saleEsService.saveNewSale(sale).subscribe(result -> logger.info("Entity has been saved: {}", result));
        return null;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update sale")
    public Sale updateSale(@RequestBody SaleSaveRequestDto item) {
        saleService.updateSale(item);
        return null;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete sale")
    public void deleteSale(@RequestBody SaleSaveRequestDto item) {
        saleService.delete(item.getId());
    }

    @PostMapping("/exportExcel")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export sale excel")
    public Object excelSale() {
        ByteArrayResource resource = saleService.excelSale(null);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
            .body(resource);
        //return resource;
    }

    @GetMapping("/exportExcelByParameters")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export sale excel with parameter")
    public Object excelSaleByParameters(@RequestParam String moneyType) {
        ByteArrayResource resource = saleService.excelSale(moneyType);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
    }

    @GetMapping("/exportSales")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export sale excel")
    public Object excelSales() {
        ByteArrayResource resource = saleEsService.excelSale(null);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
    }
}
