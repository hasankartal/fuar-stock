package com.fuar.domain.customer;

import com.fuar.domain.EntityBase;
import com.fuar.domain.country.Country;
import com.fuar.domain.invoice.Invoice;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Document(collection = "customer")
@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends EntityBase {

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "ADDRESS")
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID")
    private Country country;

  //  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "customer")
  //  private Set<Invoice> invoice = new HashSet<>();

}
