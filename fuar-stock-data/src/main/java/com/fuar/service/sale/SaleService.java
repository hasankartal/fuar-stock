package com.fuar.service.sale;

import com.fuar.entity.invoice.Invoice;
import com.fuar.entity.sale.Sale;
import com.fuar.domain.sale.SaleResponseDto;
import com.fuar.domain.sale.SaleSaveRequestDto;
import com.fuar.repository.sale.SaleRepository;
import com.fuar.service.invoice.InvoiceService;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SaleService {
    Logger logger = LoggerFactory.getLogger(SaleService.class);

    @Autowired
    private SaleRepository saleRepository;
    //private final SaleEsService saleEsService;
    //private final SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private InvoiceService invoiceService;

    @Transactional
    public Sale saveSale(SaleSaveRequestDto request) {
        Invoice invoice = invoiceService.findById(request.getInvoiceId());

        Sale sale = Sale.builder()
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(new Date())
                .invoice(invoice)
                .build();

        Sale savedSale = saleRepository.save(sale);
        //saleEsService.saveNewSaleEs(sale);
        return savedSale;
    }

    @Transactional
    public Sale updateSale(SaleSaveRequestDto request) {
        Sale sale = saleRepository.findById(request.getId()).orElse(null);

        sale.setId(request.getId());
        sale.setAmount(request.getAmount());
        sale.setMoney(request.getMoneyType());
        sale.setOrderDate(request.getOrderDate());
        sale.setOperation("UPDATED");

        Sale updatedSale = saleRepository.save(sale);

        return updatedSale;
    }

    public SaleResponseDto save(SaleSaveRequestDto request) {
        Sale sale = Sale.builder()
              //  .id(request.getId())
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(request.getOrderDate())
                .build();
        saleRepository.save(sale);

        return null;
        //return this.mapToDto(saleEsService.saveNewSale(sale).publishOn(Schedulers.elastic()).block());
    }

    @Transactional
    public void delete(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);
        if(sale != null) {
            saleRepository.deleteById(id);
        }
    }

    public List<SaleResponseDto> fetchAllSales() {
        List<Sale> saleList = findAll();
        List<SaleResponseDto> saleResponseDtoList = new ArrayList<>();
        for (Sale sale : saleList) {
            SaleResponseDto saleResponseDto = mapToDto(sale);
            saleResponseDtoList.add(saleResponseDto);
        }
        return saleResponseDtoList;
    }

    public List<Sale> findAll() {
        return saleRepository.findAll(sortByOrderDateDesc());
    }

    public Sale findById(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);

        return sale.get();
    }

    public List<SaleResponseDto> findByMoneyType(String moneyType) {
        List<Sale> saleList = saleRepository.findByMoney(moneyType);
        List<SaleResponseDto> saleResponseDtoList = new ArrayList<>();
        for (Sale sale : saleList) {
            SaleResponseDto saleResponseDto = mapToDto(sale);
            saleResponseDtoList.add(saleResponseDto);
        }

        return saleResponseDtoList;
    }

    private Sort sortByOrderDateDesc() {
        return Sort.by(Sort.Direction.DESC, "orderDate");
    }

    private SaleResponseDto mapToDto(Sale item) {
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
                .invoiceValue(item.getInvoice().getInvoiceId())
                .invoiceId(item.getInvoice().getId())
                .build();
    }

    public ByteArrayResource excelSale(String moneyType) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        arrangeHeader(sheet);

        List<SaleResponseDto> saleResponseDtoList = null;

        if (moneyType == null) {
            saleResponseDtoList = fetchAllSales();
        } else {
            saleResponseDtoList = findByMoneyType(moneyType);
        }

        int numberOfRow = 1;
        for (SaleResponseDto saleResponseDto: saleResponseDtoList) {
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

}
