package com.fuar.repository.collection;

import com.fuar.domain.collection.CollectionProjectionResponseDto;
import com.fuar.domain.collection.CollectionResponseDto;
import com.fuar.entity.collection.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

//    @Query(value = " SELECT new com.fuar.domain.collection.CollectionProjectionResponseDto(SUM(c.amount) as amount, c.moneyType as moneyType, c.customer.id as customerId) " +
//            " FROM Collection AS c GROUP BY c.amount, c.moneyType,c.customer.id " +
//            " ORDER BY c.customer.id DESC")
    @Query(value = " SELECT amount, moneyType, customerId, name, surname FROM " +
            " ( SELECT sum(co.amount) as amount, co.money_type as moneyType, co.customer_id as customerId, customer.name, customer.surname " +
            " from collection co " +
            "   inner join customer customer on co.customer_id = customer.id " +
            " group by co.customer_id, co.money_type, customer.name, customer.surname) as sub", nativeQuery = true)
    List<CollectionProjectionResponseDto> sumAmountByCustomer();
}