package com.fuar.service.invoice;

import com.fuar.domain.invoice.Invoice;
import com.fuar.model.invoice.InvoiceResponseDto;
import com.fuar.model.invoice.InvoiceSaveRequestDto;
import com.fuar.repository.invoice.InvoiceRepository;
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
public class InvoiceService {

    Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;
    //private final SaleEsService saleEsService;
    //private final SequenceGeneratorService sequenceGeneratorService;

    @Transactional
    public Invoice saveInvoice(InvoiceSaveRequestDto request) {
        String operationType = "CREATED";

        Invoice invoice = Invoice.builder()
                //          .id(sequenceGeneratorService.generateSequence(Sale.SEQUENCE_NAME))
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(new Date())
                .operation(operationType)
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);
        //saleEsService.saveNewSaleEs(sale);
        return savedInvoice;
    }

    @Transactional
    public Invoice updateInvoice(InvoiceSaveRequestDto request) {
        Invoice invoice = invoiceRepository.findById(request.getId()).orElse(null);

        invoice.setId(request.getId());
        invoice.setAmount(request.getAmount());
        invoice.setMoney(request.getMoneyType());
        invoice.setOrderDate(request.getOrderDate());
        invoice.setOperation("UPDATED");

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        return updatedInvoice;
    }

    public InvoiceResponseDto save(InvoiceSaveRequestDto request) {
        Invoice sale = Invoice.builder()
                //  .id(request.getId())
                .amount(request.getAmount())
                .money(request.getMoneyType())
                .orderDate(request.getOrderDate())
                .build();
        invoiceRepository.save(sale);

        return null;
        //return this.mapToDto(saleEsService.saveNewSale(sale).publishOn(Schedulers.elastic()).block());
    }

    @Transactional
    public void delete(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if(invoice != null) {
            invoiceRepository.deleteById(id);
        }
    }

    public List<InvoiceResponseDto> fetchAllInvoices() {
        List<Invoice> invoiceList = findAll();
        List<InvoiceResponseDto> invoiceResponseDtoList = new ArrayList<>();
        for (Invoice invoice : invoiceList) {
            InvoiceResponseDto invoiceResponseDto = mapToDto(invoice);
            invoiceResponseDtoList.add(invoiceResponseDto);
        }
        return invoiceResponseDtoList;
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll(sortByOrderDateDesc());
    }

    public List<InvoiceResponseDto> findByMoneyType(String moneyType) {
        List<Invoice> invoiceList = invoiceRepository.findByMoney(moneyType);
        List<InvoiceResponseDto> invoiceResponseDtoList = new ArrayList<>();
        for (Invoice invoice : invoiceList) {
            InvoiceResponseDto invoiceResponseDto = mapToDto(invoice);
            invoiceResponseDtoList.add(invoiceResponseDto);
        }

        return invoiceResponseDtoList;
    }

    private Sort sortByOrderDateDesc() {
        return Sort.by(Sort.Direction.DESC, "orderDate");
    }

    private InvoiceResponseDto mapToDto(Invoice item) {
        if (item == null) {
            return null;
        }
//        Locale locale = new Locale("tr", "TR");
//        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
//        String date = dateFormat.format(item.getOrderDate());
        // String date = item.getOrderDate().toLocaleString();
        return InvoiceResponseDto.builder()
                .id(item.getId())
                .amount(item.getAmount())
                .moneyType(item.getMoney())
                .orderDate(item.getOrderDate())
                .build();
    }

    public ByteArrayResource excelInvoice(String moneyType) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        arrangeHeader(sheet);

        List<InvoiceResponseDto> invoiceResponseDtoList = null;

        if (moneyType == null) {
            invoiceResponseDtoList = fetchAllInvoices();
        } else {
            invoiceResponseDtoList = findByMoneyType(moneyType);
        }

        int numberOfRow = 1;
        for (InvoiceResponseDto invoiceResponseDto : invoiceResponseDtoList) {
            createNewRow(sheet, invoiceResponseDto, numberOfRow);
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

    private void createNewRow(Sheet sheet, InvoiceResponseDto invoiceResponseDto, int numberOfRow) {
        Row row = sheet.createRow(numberOfRow);
        int numberOfColumn = 0;
        Cell idCell = row.createCell(numberOfColumn);
        idCell.setCellValue(invoiceResponseDto.getId());
        numberOfColumn++;

        Cell amountCell = row.createCell(numberOfColumn);
        amountCell.setCellValue(invoiceResponseDto.getAmount() != null ? invoiceResponseDto.getAmount().toString() : "");
        numberOfColumn++;

        Cell moneyTypeCell = row.createCell(numberOfColumn);
        moneyTypeCell.setCellValue(invoiceResponseDto.getMoneyType());
        numberOfColumn++;

        Cell orderDateCell = row.createCell(numberOfColumn);
        Locale locale = new Locale("tr", "TR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(invoiceResponseDto.getOrderDate());
        orderDateCell.setCellValue(date);
        numberOfColumn++;
    }
}
