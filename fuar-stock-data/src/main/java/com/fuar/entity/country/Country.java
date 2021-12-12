package com.fuar.entity.country;

import com.fuar.entity.EntityBase;
import lombok.*;

import javax.persistence.*;

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
