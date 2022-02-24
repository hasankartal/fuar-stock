package com.fuar.api.undischarged;

import com.fuar.api.collection.CollectionApi;
import com.fuar.domain.claim.ClaimResponseDto;
import com.fuar.domain.undischarge.UndischargeResponseDto;
import com.fuar.entity.payment.Payment;
import com.fuar.service.claim.ClaimService;
import com.fuar.service.collection.CollectionService;
import com.fuar.service.payment.PaymentService;
import com.fuar.service.service.OrderService;
import com.fuar.service.undischarge.UndischargeService;
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

@RestController
@RequestMapping("/undischarge")
@RequiredArgsConstructor
@CrossOrigin("*")
@Api(value="All details about the undischarge api.")
public class UndischargeApi {

    Logger logger = LoggerFactory.getLogger(UndischargeApi.class);
    private final UndischargeService undischargeService;

    @GetMapping
    @ApiOperation(value = "Retrieve all collections")
    public List<UndischargeResponseDto> fetchAllUndischarge() {
        List<UndischargeResponseDto> undischargeResponseDto = undischargeService.fetchAllUndischarge();

        return undischargeResponseDto;
    }

}
