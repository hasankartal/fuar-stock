package com.fuar.service.undischarge;

import com.fuar.domain.payment.PaymentProjectionResponseDto;
import com.fuar.domain.undischarge.UndischargeResponseDto;
import com.fuar.service.payment.PaymentService;
import com.fuar.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UndischargeService {

    Logger logger = LoggerFactory.getLogger(UndischargeService.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    public List<UndischargeResponseDto> fetchAllUndischarge() {
        List<PaymentProjectionResponseDto> paymentResponseDto = paymentService.sumAmountByCustomer();
        List<PaymentProjectionResponseDto> orderResponseDto = orderService.sumAmountByCustomer();

        List<UndischargeResponseDto> paymentResponseDtoStream = paymentResponseDto
                .parallelStream().map( t-> {
                    UndischargeResponseDto undischargeResponseDto = new UndischargeResponseDto();
                    undischargeResponseDto.setAmount(t.getAmount());
                    undischargeResponseDto.setMoneyType(t.getMoneyType());
                    undischargeResponseDto.setCustomerId(t.getCustomerId());
                    undischargeResponseDto.setCustomer(t.getName() + " " + t.getSurname());
                    return undischargeResponseDto;
                }).collect(Collectors.toList());

        List<UndischargeResponseDto> orderResponseDtoStream = orderResponseDto
                .parallelStream().map( t-> {
                    UndischargeResponseDto undischargeResponseDto = new UndischargeResponseDto();
                    undischargeResponseDto.setAmount(t.getAmount());
                    undischargeResponseDto.setMoneyType(t.getMoneyType());
                    undischargeResponseDto.setCustomerId(t.getCustomerId());
                    undischargeResponseDto.setCustomer(t.getName() + " " + t.getSurname());
                    return undischargeResponseDto;
                }).collect(Collectors.toList());

        Map<String, UndischargeResponseDto> paymentMap = paymentResponseDtoStream.parallelStream()
                .collect(Collectors.toMap(UndischargeResponseDto::keyOfClaim, Function.identity()));

        Map<String, UndischargeResponseDto> orderMap = orderResponseDtoStream.parallelStream()
                .collect(Collectors.toMap(UndischargeResponseDto::keyOfClaim, Function.identity()));

        Map<String, UndischargeResponseDto> differenceMap = Stream.of(paymentMap, orderMap)
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

        List<UndischargeResponseDto> undischargeResponseDtoList = differenceMap.values().stream().collect(Collectors.toList());

        return undischargeResponseDtoList;
    }
}
