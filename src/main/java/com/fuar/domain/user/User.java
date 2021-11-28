package com.fuar.domain.user;

import com.fuar.domain.EntityBase;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Document(collection = "user")
@Entity
@Table
@Data
@NoArgsConstructor
public class User extends EntityBase {

    private String userName;
    private String password;

}
