package com.fuar.api.claim;

import com.fuar.api.collection.CollectionApi;
import com.fuar.domain.claim.ClaimResponseDto;
import com.fuar.domain.collection.CollectionProjectionResponseDto;
import com.fuar.domain.collection.CollectionResponseDto;
import com.fuar.service.claim.ClaimService;
import com.fuar.service.collection.CollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/claim")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the claim api.")
public class ClaimApi {

    Logger logger = LoggerFactory.getLogger(CollectionApi.class);
    private final ClaimService claimService;

    @GetMapping
    @ApiOperation(value = "Retrieve all collections")
    public List<ClaimResponseDto> fetchAllClaims() {
        List<ClaimResponseDto> claimResponseDto = claimService.fetchAllClaims();

        return claimResponseDto;
    }

}
