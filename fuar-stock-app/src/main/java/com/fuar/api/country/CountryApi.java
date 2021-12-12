package com.fuar.api.country;

import com.fuar.entity.country.Country;
import com.fuar.domain.country.CountryResponseDto;
import com.fuar.domain.country.CountrySaveRequestDto;
import com.fuar.domain.country.CountrySearchRequestDto;
import com.fuar.service.country.CountryService;
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
@RequestMapping("/country")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the country api.")
public class CountryApi {

    Logger logger = LoggerFactory.getLogger(CountryApi.class);
    private final CountryService countryService;

    @GetMapping
    @ApiOperation(value = "Retrieve all countries")
    public List<CountryResponseDto> fetchAllCountries() {
        return countryService.fetchAllCountries();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Retrieve countries by parameters")
    public List<CountryResponseDto> fetchCountriesByParameters(@Valid CountrySearchRequestDto countrySearchRequestDto) {
        return countryService.findByCodeOrName(countrySearchRequestDto.getCode(), countrySearchRequestDto.getName());
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new country")
    public Country saveCountry(@RequestBody CountrySaveRequestDto item) {
        Country country = countryService.saveCountry(item);
        return null;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update country")
    public Country updateCountry(@RequestBody CountrySaveRequestDto item) {
        countryService.updateCountry(item);
        return null;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete country")
    public void deleteCountry(@RequestBody CountrySaveRequestDto item) {
        countryService.delete(item.getId());
    }

    @PostMapping("/exportExcel")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export country excel")
    public Object excelCountry() {
        ByteArrayResource resource = countryService.excelCountry(null, null);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
        //return resource;
    }

    @GetMapping("/exportExcelByParameters")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export country excel with parameter")
    public Object excelCountryByParameters(@RequestParam String code, @RequestParam String name) {
        ByteArrayResource resource = countryService.excelCountry(code, name);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
    }

    @GetMapping("/exportCountries")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export countries excel")
    public Object excelCountries() {
        ByteArrayResource resource = countryService.excelCountry(null, null);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
    }

}
