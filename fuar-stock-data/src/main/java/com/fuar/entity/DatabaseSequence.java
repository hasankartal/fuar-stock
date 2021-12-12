package com.fuar.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Data
@Getter
@Setter
@NoArgsConstructor
//@Document(collection = "database_sequences")
public class DatabaseSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private long seq;

}