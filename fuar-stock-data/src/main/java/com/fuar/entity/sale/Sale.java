package com.fuar.entity.sale;

import com.fuar.entity.EntityBase;
import com.fuar.entity.invoice.Invoice;
import lombok.*;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.Date;

//@Document(collection = "sale")
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Sale extends EntityBase {

    @Transient
    public static final String SEQUENCE_NAME = "sale_seq";

    private Float amount;
    private String money;
    private Date orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "ID")
    private Invoice invoice;

    @Builder
    public Sale(long id, Float amount, String money, Invoice invoice, Date orderDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.amount = amount;
        this.money = money;
        this.invoice = invoice;
        this.orderDate = orderDate;
    }
}
