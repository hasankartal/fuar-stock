package com.fuar.service.sale.es;

import com.fuar.domain.sale.Sale;
import com.fuar.domain.sale.es.SaleEs;
import com.fuar.model.sale.SaleResponseDto;
import com.fuar.repository.sale.es.SaleEsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SaleEsService {
    Logger logger = LoggerFactory.getLogger(SaleEsService.class);

    private final SaleEsRepository saleEsRepository;

    public Flux<SaleResponseDto> fetchAllSales() {
        return findAll().map(this::mapToDto);
    }

    public Flux<SaleEs> findAll() {
        return saleEsRepository.findAll(sortByOrderDateDesc());
    }

    private SaleResponseDto mapToDto(SaleEs item) {
        if (item == null) {
            return null;
        }
//        Locale locale = new Locale("tr", "TR");
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
//        String date = dateFormat.format(item.getOrderDate());
       // String date = item.getOrderDate().toLocaleString();
        return SaleResponseDto.builder()
                .id(item.getId())
                .amount(item.getAmount())
                .moneyType(item.getMoney())
                .orderDate(item.getOrderDate())
                .build();
    }

    public Flux<SaleResponseDto> findByMoneyType(String moneyType) {
        return saleEsRepository.findByMoney(moneyType).map(this::mapToDto);
    }

    public Mono<SaleEs> saveNewSaleEs(Sale sale) {
        Mono<SaleEs> ps = saleEsRepository.save(
                SaleEs.builder()
                        .id(sale.getId())
                        .amount(sale.getAmount())
                        .money(sale.getMoney())
                        .orderDate(new Date())
                        .build());
        return ps;
    }

    public Mono<SaleEs> updateSaleEs(Sale sale) {
        SaleEs saleEs = saleEsRepository.findById(sale.getId()).block();

        saleEs.setId(sale.getId());
        saleEs.setAmount(sale.getAmount());
        saleEs.setMoney(sale.getMoney());
        saleEs.setOrderDate(sale.getOrderDate());

        Mono<SaleEs> ps = saleEsRepository.save(saleEs);
        return ps;
    }

    public void delete(Long id) {
        saleEsRepository.deleteById(id).subscribe(result -> logger.info("Entity has been deleted from elastic search: {}", result));
    }

    public ByteArrayResource excelSale(String moneyType) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        arrangeHeader(sheet);

        Flux<SaleResponseDto> saleResponseDtoFlux = null;
        
        if (moneyType == null) {
            saleResponseDtoFlux = fetchAllSales();
        } else {
            saleResponseDtoFlux = findByMoneyType(moneyType);
        }
        List<SaleResponseDto> saleResponseDtoList = saleResponseDtoFlux.collectList().block();
        int numberOfRow = 1;
        for (SaleResponseDto saleResponseDto: saleResponseDtoList ) {
            createNewRow(sheet, saleResponseDto, numberOfRow);
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

    private void createNewRow(Sheet sheet, SaleResponseDto saleResponseDto, int numberOfRow) {
        Row row = sheet.createRow(numberOfRow);
        int numberOfColumn = 0;
        Cell idCell = row.createCell(numberOfColumn);
        idCell.setCellValue(saleResponseDto.getId());
        numberOfColumn++;

        Cell amountCell = row.createCell(numberOfColumn);
        amountCell.setCellValue(saleResponseDto.getAmount() != null ? saleResponseDto.getAmount().toString() : "");
        numberOfColumn++;

        Cell moneyTypeCell = row.createCell(numberOfColumn);
        moneyTypeCell.setCellValue(saleResponseDto.getMoneyType());
        numberOfColumn++;

        Cell orderDateCell = row.createCell(numberOfColumn);
        Locale locale = new Locale("tr", "TR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(saleResponseDto.getOrderDate());
        orderDateCell.setCellValue(date);
        numberOfColumn++;
    }

    private Sort sortByOrderDateDesc() {
        return Sort.by(Sort.Direction.DESC, "orderDate");
    }
}