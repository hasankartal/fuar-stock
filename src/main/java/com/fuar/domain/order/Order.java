package com.fuar.domain.order;

import com.fuar.domain.EntityBase;
import com.fuar.domain.customer.Customer;
import com.fuar.domain.invoice.Invoice;
import com.fuar.domain.sale.Sale;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ORDER_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class Order extends EntityBase {

    private Float amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SALE_ID", referencedColumnName = "ID")
    private Sale sale;

    @Builder
    public Order(long id, Float amount, String money, Customer customer, Sale sale, Date orderDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.amount = amount;
        this.customer = customer;
        this.sale = sale;
    }

}
