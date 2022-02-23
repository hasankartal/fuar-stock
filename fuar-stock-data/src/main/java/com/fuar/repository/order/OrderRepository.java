package com.fuar.repository.order;

import com.fuar.domain.payment.PaymentProjectionResponseDto;
import com.fuar.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = " SELECT amount, moneyType, customerId, name, surname FROM " +
            " ( select sum(ord.amount) as amount, ord.money_type as moneyType, ord.customer_id as customerId, c.name as name, c.surname " +
            " from order_table ord inner join customer c on ord.customer_id = c.id " +
            " group by ord.money_type, ord.customer_id, c.name, c.surname ) as sub", nativeQuery = true)
    List<PaymentProjectionResponseDto> sumAmountByCustomer();
}
