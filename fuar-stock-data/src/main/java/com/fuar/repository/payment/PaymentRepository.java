package com.fuar.repository.payment;

import com.fuar.domain.collection.CollectionProjectionResponseDto;
import com.fuar.domain.payment.PaymentProjectionResponseDto;
import com.fuar.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = " SELECT amount, moneyType, customerId, name, surname FROM " +
            " ( select sum(payment.amount) as amount, payment.money_type as moneyType, payment.customer_id as customerId, c.name as name, c.surname as surname" +
            " from payment payment inner join customer c on payment.customer_id = c.id " +
            " group by payment.money_type, payment.customer_id, c.name, c.surname) as sub", nativeQuery = true)
    List<PaymentProjectionResponseDto> sumAmountByCustomer();

}
