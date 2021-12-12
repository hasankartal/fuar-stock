package com.fuar.entity.user;

import com.fuar.entity.EntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//@Document(collection = "user")
@Entity
@Table(name = "userr")
@Getter
@Setter
@NoArgsConstructor
public class User extends EntityBase {

    private String userName;
    private String password;

    @Builder
    public User(long id, String userName, String password, String operation) {
        super(id, new Date(), operation, new Date());
        this.userName = userName;
        this.password = password;
    }
}
