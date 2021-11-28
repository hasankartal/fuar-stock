package com.fuar.domain.log;

import com.fuar.domain.EntityBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Document(collection = "token")
@Entity
@Table
@Data
public class Token extends EntityBase {

    private Boolean isActive;
    private String token;
}