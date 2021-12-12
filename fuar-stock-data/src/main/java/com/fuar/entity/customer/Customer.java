package com.fuar.entity.customer;

import com.fuar.entity.EntityBase;
import com.fuar.entity.country.Country;
import lombok.*;

import javax.persistence.*;

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
