package com.fuar.domain.sale;

import com.fuar.domain.EntityBase;
import com.fuar.domain.customer.Customer;
import lombok.*;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//@Document(collection = "sale")
@Entity
@Table
@Data
@NoArgsConstructor
public class Sale extends EntityBase {

    @Transient
    public static final String SEQUENCE_NAME = "sale_seq";

    private Float amount;
    private String money;
    private Date orderDate;

    @Builder
    public Sale(long id, Float amount, String money, Date orderDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.amount = amount;
        this.money = money;
        this.orderDate = orderDate;
    }
}
