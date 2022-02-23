package com.fuar.api.collection;

import com.fuar.domain.collection.CollectionResponseDto;
import com.fuar.domain.collection.CollectionSaveRequestDto;
import com.fuar.domain.collection.CollectionSearchRequestDto;
import com.fuar.entity.collection.Collection;
import com.fuar.service.collection.CollectionService;
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
@RequestMapping("/collection")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the collection api.")
public class CollectionApi {
    Logger logger = LoggerFactory.getLogger(CollectionApi.class);
    private final CollectionService collectionService;

    @GetMapping
    @ApiOperation(value = "Retrieve all collections")
    public List<CollectionResponseDto> fetchAllCollections() {
        return collectionService.fetchAllCollections();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Retrieve collections by parameters")
    public List<CollectionResponseDto> fetchCollectionsByParameters(@Valid CollectionSearchRequestDto collectionSearchRequestDto) {
//        return collectionService.findByMoneyType(saleSearchRequestDto.getMoneyType());
        return null;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Post new collection")
    public Collection saveCollection(@RequestBody CollectionSaveRequestDto item) {
        Collection collection = collectionService.saveCollection(item);

        return null;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update collection")
    public Collection updatCollection(@RequestBody CollectionSaveRequestDto item) {
        collectionService.updateCollection(item);
        return null;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete collection")
    public void deleteCollection(@RequestBody CollectionSaveRequestDto item) {
        collectionService.delete(item.getId());
    }

    @PostMapping("/exportExcel")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export collection excel")
    public Object excelCollection() {
        ByteArrayResource resource = collectionService.excelCollection(null);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
        //return resource;
    }

    @GetMapping("/exportExcelByParameters")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Export collection excel with parameter")
    public Object excelCollectionByParameters(@RequestParam String moneyType) {
        ByteArrayResource resource = collectionService.excelCollection(moneyType);

        return ResponseEntity
                .ok()
                .contentType(new MediaType("application", "vnd.ms-excel"))
                .body(resource);
    }
}
