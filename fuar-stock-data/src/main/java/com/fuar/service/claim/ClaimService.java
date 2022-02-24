package com.fuar.service.claim;

import com.fuar.domain.claim.ClaimResponseDto;
import com.fuar.domain.collection.CollectionProjectionResponseDto;
import com.fuar.domain.collection.CollectionResponseDto;
import com.fuar.entity.collection.Collection;
import com.fuar.entity.sale.Sale;
import com.fuar.service.collection.CollectionService;
import com.fuar.service.sale.SaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ClaimService {
    Logger logger = LoggerFactory.getLogger(ClaimService.class);

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private SaleService saleService;

    public List<ClaimResponseDto> fetchAllClaims() {
        List<CollectionProjectionResponseDto> collectionResponseDto = collectionService.sumAmountByCustomer();
        List<CollectionProjectionResponseDto> saleResponseDto = saleService.sumAmountByCustomer();

        List<ClaimResponseDto> collectionResponseDtoStream = collectionResponseDto
            .parallelStream().map( t-> {
                ClaimResponseDto claimResponseDto = new ClaimResponseDto();
                claimResponseDto.setAmount(t.getAmount());
                claimResponseDto.setMoneyType(t.getMoneyType());
                claimResponseDto.setCustomerId(t.getCustomerId());
                claimResponseDto.setCustomer(t.getName() + " " + t.getSurname());
                return claimResponseDto;
            }).collect(Collectors.toList());

        List<ClaimResponseDto> saleResponseDtoStream = saleResponseDto
                .parallelStream().map( t-> {
                    ClaimResponseDto claimResponseDto = new ClaimResponseDto();
                    claimResponseDto.setAmount(t.getAmount());
                    claimResponseDto.setMoneyType(t.getMoneyType());
                    claimResponseDto.setCustomerId(t.getCustomerId());
                    claimResponseDto.setCustomer(t.getName() + " " + t.getSurname());
                    return claimResponseDto;
                }).collect(Collectors.toList());

        Map<String, ClaimResponseDto> collectionMap = collectionResponseDtoStream.parallelStream()
                .collect(Collectors.toMap(ClaimResponseDto::keyOfClaim, Function.identity()));

        Map<String, ClaimResponseDto> saleMap = saleResponseDtoStream.parallelStream()
                .collect(Collectors.toMap(ClaimResponseDto::keyOfClaim, Function.identity()));

        Map<String, ClaimResponseDto> differenceMap = Stream.of(collectionMap, saleMap)
            .parallel().flatMap(map -> map.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1,v2) -> {
                if(v1 != null) {
                    if (v2 != null) {
                        v2.setAmount(v2.getAmount() - v1.getAmount());
                        return v2;
                    }
                }
                v1.setAmount( (-1) * v1.getAmount());
                return v1;
            }));

        List<ClaimResponseDto> claimResponseDtoList = differenceMap.values().stream().collect(Collectors.toList());
        /*
        for (Map.Entry<String, ClaimResponseDto> entry : differenceMap.entrySet()) {
            if (entry.getValue() != null) {
                claimResponseDtoList.add(entry.getValue());
            }
        }
        */

        return claimResponseDtoList;
    }
}
