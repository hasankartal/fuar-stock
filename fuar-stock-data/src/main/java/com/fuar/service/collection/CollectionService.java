package com.fuar.service.collection;

import com.fuar.domain.collection.CollectionProjectionResponseDto;
import com.fuar.domain.collection.CollectionResponseDto;
import com.fuar.domain.collection.CollectionSaveRequestDto;
import com.fuar.entity.collection.Collection;
import com.fuar.entity.customer.Customer;
import com.fuar.repository.collection.CollectionRepository;
import com.fuar.service.customer.CustomerService;
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

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CollectionService {
    Logger logger = LoggerFactory.getLogger(CollectionService.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CollectionRepository collectionRepository;

    @Transactional
    public Collection saveCollection(CollectionSaveRequestDto request) {
        Customer customer = customerService.findById(request.getCustomerId());

        Collection collection = Collection.builder()
                .customer(customer)
                .amount(request.getAmount())
                .moneyType(request.getMoneyType())
                .paymentDate(new Date())
                .build();

        Collection savedCollection = collectionRepository.save(collection);
        //saleEsService.saveNewSaleEs(sale);
        return savedCollection;
    }

    @Transactional
    public Collection updateCollection(CollectionSaveRequestDto request) {
        Collection collection = collectionRepository.findById(request.getId()).orElse(null);

        collection.setId(request.getId());
        collection.setAmount(request.getAmount());
        collection.setMoneyType(request.getMoneyType());
        collection.setPaymentDate(request.getPaymentDate());
        collection.setOperation("UPDATED");

        Collection updatedCollection = collectionRepository.save(collection);

        return updatedCollection;
    }

    public CollectionResponseDto save(CollectionSaveRequestDto request) {
        Collection collection = Collection.builder()
                //  .id(request.getId())
                .amount(request.getAmount())
                .moneyType(request.getMoneyType())
                .paymentDate(request.getPaymentDate())
                .build();
        collectionRepository.save(collection);

        return null;
        //return this.mapToDto(saleEsService.saveNewSale(sale).publishOn(Schedulers.elastic()).block());
    }

    @Transactional
    public void delete(Long id) {
        Optional<Collection> collection = collectionRepository.findById(id);
        if(collection != null) {
            collectionRepository.deleteById(id);
        }
    }

    public Collection findById(Long id) {
        Optional<Collection> collection = collectionRepository.findById(id);

        return collection.get();
    }

    public List<CollectionResponseDto> findByMoneyType(String moneyType) {
        //List<Collection> collectionList = collectionRepository.findByMoney(moneyType);
        List<Collection> collectionList = collectionRepository.findAll();

        List<CollectionResponseDto> collectionResponseDtoList = new ArrayList<>();
        for (Collection collection : collectionList) {
            CollectionResponseDto collectionResponseDto = mapToDto(collection);
            collectionResponseDtoList.add(collectionResponseDto);
        }

        return collectionResponseDtoList;
    }

    private Sort sortByOrderDateDesc() {
        return Sort.by(Sort.Direction.DESC, "orderDate");
    }

    public ByteArrayResource excelCollection(String moneyType) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        arrangeHeader(sheet);

        List<CollectionResponseDto> collectionResponseDtoList = null;

        if (moneyType == null) {
            collectionResponseDtoList = fetchAllCollections();
        } else {
            collectionResponseDtoList = findByMoneyType(moneyType);
        }

        int numberOfRow = 1;
        for (CollectionResponseDto collectionResponseDto: collectionResponseDtoList) {
            createNewRow(sheet, collectionResponseDto, numberOfRow);
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

    private void createNewRow(Sheet sheet, CollectionResponseDto collectionResponseDto, int numberOfRow) {
        Row row = sheet.createRow(numberOfRow);
        int numberOfColumn = 0;
        Cell idCell = row.createCell(numberOfColumn);
        idCell.setCellValue(collectionResponseDto.getId());
        numberOfColumn++;

        Cell amountCell = row.createCell(numberOfColumn);
        amountCell.setCellValue(collectionResponseDto.getAmount() != null ? collectionResponseDto.getAmount().toString() : "");
        numberOfColumn++;

        Cell moneyTypeCell = row.createCell(numberOfColumn);
        moneyTypeCell.setCellValue(collectionResponseDto.getMoneyType());
        numberOfColumn++;

        Cell orderDateCell = row.createCell(numberOfColumn);
        Locale locale = new Locale("tr", "TR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String date = dateFormat.format(collectionResponseDto.getPaymentDate());
        orderDateCell.setCellValue(date);
        numberOfColumn++;
    }

    public List<CollectionResponseDto> fetchAllCollections() {
        List<Collection> collectionList = findAll();
        List<CollectionResponseDto> collectionResponseDtoList = new ArrayList<>();
        for (Collection collection : collectionList) {
            CollectionResponseDto collectionResponseDto = mapToDto(collection);
            collectionResponseDtoList.add(collectionResponseDto);
        }
        return collectionResponseDtoList;
    }

    public List<CollectionProjectionResponseDto> sumAmountByCustomer() {
        List<CollectionProjectionResponseDto> collectionList = collectionRepository.sumAmountByCustomer();

        return collectionList;
    }

    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    private CollectionResponseDto mapToDto(Collection item) {
        if (item == null) {
            return null;
        }
        Locale locale = new Locale("tr", "TR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String paymentDate = dateFormat.format(item.getPaymentDate());
        // String date = item.getOrderDate().toLocaleString();
        return CollectionResponseDto.builder()
                .id(item.getId())
                .amount(item.getAmount())
                .moneyType(item.getMoneyType())
                .paymentDate(item.getPaymentDate())
                .customerId(item.getCustomer().getId())
                .customer(item.getCustomer().getName() + " " + item.getCustomer().getSurname())
                .build();
    }
}