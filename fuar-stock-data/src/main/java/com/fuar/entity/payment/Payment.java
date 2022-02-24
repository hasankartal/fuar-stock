package com.fuar.entity.payment;

import com.fuar.entity.EntityBase;
import com.fuar.entity.customer.Customer;
import com.fuar.entity.order.Order;
import com.fuar.entity.sale.Sale;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Payment extends EntityBase {

    private Float amount;
    private String moneyType;
    private Date paymentDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    @Builder
    public Payment(long id, Float amount, String moneyType, Customer customer, String operation) {
        super(id, new Date(), operation, new Date());
        this.amount = amount;
        this.moneyType = moneyType;
        this.customer = customer;
    }

}
