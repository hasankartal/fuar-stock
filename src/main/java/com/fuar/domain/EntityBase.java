package com.fuar.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityBase implements Serializable {

    @Id
    @SequenceGenerator(name="webuser_idwebuser_seq",
            sequenceName="webuser_idwebuser_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="webuser_idwebuser_seq")
    @Column(name = "ID")
    private Long id;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "OPERATION")
    private String operation;

    @LastModifiedDate
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @PrePersist
    public void onPrePersist() {
        this.setOperation("SAVE");
        this.setCreatedDate(new Date());
        this.setUpdatedDate(new Date());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setOperation("UPDATE");
        this.setUpdatedDate(new Date());
    }
}
