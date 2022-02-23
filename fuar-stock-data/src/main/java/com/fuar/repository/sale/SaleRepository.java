package com.fuar.repository.sale;

import com.fuar.domain.collection.CollectionProjectionResponseDto;
import com.fuar.entity.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByMoney(String money);

    @Query(value = " select amount, moneyType, customerId, name, surname from " +
            " (select sum(sl.amount) as amount, sl.money as moneyType, invoice.customer_id as customerId, customer.name as name, customer.surname as surname " +
            " from sale sl" +
            "   inner join invoice invoice on sl.invoice_id = invoice.id " +
            "   inner join customer customer on invoice.customer_id = customer.id " +
            " group by sl.money, invoice.customer_id, customer.name, customer.surname) as sub ", nativeQuery = true)
    List<CollectionProjectionResponseDto> sumAmountByCustomer();
}
