package com.fuar.domain.invoice;

import com.fuar.domain.EntityBase;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@Data
@NoArgsConstructor
public class Invoice extends EntityBase {

    private Float amount;
    private String money;
    private Date orderDate;

    @Builder
    public Invoice(long id, Float amount, String money, Date orderDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.amount = amount;
        this.money = money;
        this.orderDate = orderDate;
    }

}
