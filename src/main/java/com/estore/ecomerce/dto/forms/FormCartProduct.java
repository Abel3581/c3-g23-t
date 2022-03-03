package com.estore.ecomerce.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormCartProduct {
    private Long id;
    private int amount = 0;
}
