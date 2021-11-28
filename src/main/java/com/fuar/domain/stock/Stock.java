package com.fuar.domain.stock;

import com.fuar.domain.EntityBase;
import com.fuar.domain.customer.Customer;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//@Document(collection = "stock")
@Entity
@Table
@Data
@NoArgsConstructor
public class Stock extends EntityBase {
    private String stock;

    @Builder
    public Stock(long id, String stock, Date orderDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.stock = stock;
    }
}
