
package com.estore.ecomerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
public class PurchaseReportRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "quantity", nullable = false, updatable = false)
    private Integer quantity = 0;

    @NotEmpty
    @CreationTimestamp   
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate = LocalDateTime.now();
}
