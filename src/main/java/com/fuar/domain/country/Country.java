package com.fuar.domain.country;

import com.fuar.domain.EntityBase;
import com.fuar.domain.customer.Customer;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country extends EntityBase {

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

 //   @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "country")
 //   private Set<Customer> customers = new HashSet<>();

}
