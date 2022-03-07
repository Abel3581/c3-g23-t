package com.estore.ecomerce.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "creationDate", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "observation", updatable = true, nullable = true, length=150)
    private String observation;

    /*Relationship!! */

    @OneToOne(cascade = {})
    private Cart cart;
 
}
