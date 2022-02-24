package com.fuar.entity.invoice;

import com.fuar.entity.EntityBase;
import com.fuar.entity.customer.Customer;
import com.fuar.entity.sale.Sale;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Invoice extends EntityBase {

    @Column(unique = true)
    private Long invoiceId;
    private String money;
    private Date orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY, mappedBy = "invoice")
    private Set<Sale> sale = new HashSet<>();

    @Builder
    public Invoice(long id, String money, Customer customer, Long invoiceId, Date orderDate, String operation) {
        super(id, new Date(), operation, new Date());
        this.money = money;
        this.orderDate = orderDate;
        this.customer = customer;
        this.invoiceId = invoiceId;
    }

}
