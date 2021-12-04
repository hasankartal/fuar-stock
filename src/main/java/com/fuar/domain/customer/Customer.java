package com.fuar.domain.customer;

import com.fuar.domain.EntityBase;
import com.fuar.domain.country.Country;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

//@Document(collection = "customer")
@Entity
@Table
@Data
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
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    private Country country;

}
