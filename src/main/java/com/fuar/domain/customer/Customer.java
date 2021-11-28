package com.fuar.domain.customer;

import com.fuar.domain.EntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//@Document(collection = "customer")
@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends EntityBase {

    private String name;
    private String surname;

}
