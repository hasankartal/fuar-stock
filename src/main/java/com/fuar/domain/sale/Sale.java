package com.fuar.domain.sale;

import com.fuar.domain.EntityBase;
import com.fuar.domain.customer.Customer;
import com.fuar.domain.invoice.Invoice;
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
    public Sale(long id, Float amount, String money, Date orderDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.amount = amount;
        this.money = money;
        this.orderDate = orderDate;
    }
}
