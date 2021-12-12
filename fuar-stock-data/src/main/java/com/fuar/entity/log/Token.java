package com.fuar.entity.log;

import com.fuar.entity.EntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Document(collection = "token")
@Entity
@Table
@Getter
@Setter
public class Token extends EntityBase {

    private Boolean isActive;
    private String token;
}