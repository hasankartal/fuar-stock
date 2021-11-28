package com.fuar.domain.sale;

import com.fuar.domain.EntityBase;
import com.fuar.domain.customer.Customer;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "sale")
@Getter
@Setter
public class Sale extends EntityBase {

    @Transient
    public static final String SEQUENCE_NAME = "sale_seq";

    private Float amount;
    private String money;
    private Date orderDate;

    @DBRef
    private Customer customer;

    @Builder
    public Sale(long id, Float amount, String money, Date orderDate, String operation, Customer customer) {
        super(id, new Date(), operation, new Date());
        this.amount = amount;
        this.money = money;
        this.orderDate = orderDate;
        this.customer = customer;
    }
}
