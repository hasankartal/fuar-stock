package com.fuar.service.country;

import com.fuar.domain.country.Country;
import com.fuar.domain.sale.Sale;
import com.fuar.model.country.CountryResponseDto;
import com.fuar.model.country.CountrySaveRequestDto;
import com.fuar.model.sale.SaleResponseDto;
import com.fuar.repository.country.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    @Autowired
    private CountryRepository repository;

    public Country saveCountry(CountrySaveRequestDto request) {
        return  repository.save(Country.
                builder()
                .name(request.getName())
                .code(request.getCode())
                .build());
    }

    @Transactional
    public Country updateCountry(CountrySaveRequestDto request) {
        Country country = repository.findById(request.getId()).orElse(null);

        country.setId(request.getId());
        country.setCode(request.getCode());
        country.setName(request.getName());
        country.setOperation("UPDATED");

        Country updatedCountry = repository.save(country);

        return updatedCountry;
    }

    @Transactional
    public void delete(Long id) {
        Optional<Country> country = repository.findById(id);
        if(country != null) {
            repository.deleteById(id);
        }
    }

    public List<CountryResponseDto> fetchAllCountries() {
        List<Country> countryList = findAll();
        List<CountryResponseDto> countryResponseDtoList = new ArrayList<>();
        for (Country country : countryList) {
            CountryResponseDto countryResponseDto = mapToDto(country);
            countryResponseDtoList.add(countryResponseDto);
        }
        return countryResponseDtoList;
    }

    public List<Country> findAll() {
        return repository.findAll();
    }

    private CountryResponseDto mapToDto(Country item) {
        if (item == null) {
            return null;
        }

        return CountryResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .code(item.getCode())
                .build();
    }

    public List<CountryResponseDto> findByCodeOrName(String code, String name) {
        List<Country> countryList = repository.findByCodeOrName(code, name);
        List<CountryResponseDto> countryResponseDtoList = new ArrayList<>();
        for (Country country : countryList) {
            CountryResponseDto countryResponseDto = mapToDto(country);
            countryResponseDtoList.add(countryResponseDto);
        }

        return countryResponseDtoList;
    }

    public Country findById(Long id) {
        Optional<Country> country = repository.findById(id);

        return country.get();
    }
    public ByteArrayResource excelCountry(String code, String name) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        arrangeHeader(sheet);

        List<CountryResponseDto> countryResponseDtoList = null;

        if (code == null || name == null) {
            countryResponseDtoList = fetchAllCountries();
        } else {
            countryResponseDtoList = findByCodeOrName(code, name);
        }

        int numberOfRow = 1;
        for (CountryResponseDto countryResponseDto : countryResponseDtoList) {
            createNewRow(sheet, countryResponseDto, numberOfRow);
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
        amount.setCellValue("Ülke Kodu");
        headerCellNumber++;

        Cell currencyType = header.createCell(headerCellNumber);
        currencyType.setCellValue("Ülke Adı");
        headerCellNumber++;

        return header;
    }

    private void createNewRow(Sheet sheet, CountryResponseDto countryResponseDto, int numberOfRow) {
        Row row = sheet.createRow(numberOfRow);
        int numberOfColumn = 0;
        Cell idCell = row.createCell(numberOfColumn);
        idCell.setCellValue(countryResponseDto.getId());
        numberOfColumn++;

        Cell codeCell = row.createCell(numberOfColumn);
        codeCell.setCellValue(countryResponseDto.getCode() != null ? countryResponseDto.getCode().toString() : "");
        numberOfColumn++;

        Cell countryNameCell = row.createCell(numberOfColumn);
        countryNameCell.setCellValue(countryResponseDto.getName());
        numberOfColumn++;
    }
}
