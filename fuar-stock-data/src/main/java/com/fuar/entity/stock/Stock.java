package com.fuar.entity.stock;

import com.fuar.entity.EntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//@Document(collection = "stock")
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Stock extends EntityBase {
    private String stock;

    @Builder
    public Stock(long id, String stock, Date orderDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.stock = stock;
    }
}
