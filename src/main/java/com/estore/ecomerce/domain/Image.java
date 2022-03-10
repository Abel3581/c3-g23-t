package com.estore.ecomerce.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel("Model Image")
public class Image {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;

    @Column(name = "name", nullable = false, updatable = true)
    private String name;

    @Column(name = "fileType", nullable = false, updatable = true)
    private String fileType;

    @Lob
    @Column(name = "fileData", nullable = false, updatable = true)
    private byte[] fileData;


}
