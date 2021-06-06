package com.fuar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class EntityBase implements Serializable {

    @Id
    private long id;
    @CreatedDate
    private Date createdDate;
    private String operation;
    @LastModifiedDate
    private Date updatedDate;
}
