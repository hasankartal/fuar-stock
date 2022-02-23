package com.fuar.entity.collection;

import com.fuar.entity.EntityBase;
import com.fuar.entity.customer.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Collection extends EntityBase {

    private Float amount;
    @Column(name = "money_type")
    private String moneyType;
    private Date paymentDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    @Builder
    public Collection(long id, Float amount, String moneyType, Customer customer, Date paymentDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.amount = amount;
        this.moneyType = moneyType;
        this.paymentDate = paymentDate;
        this.customer = customer;
    }

}
