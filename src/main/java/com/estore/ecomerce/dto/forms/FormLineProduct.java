package com.estore.ecomerce.dto.forms;

import java.util.ArrayList;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormLineProduct {
    private List<FormCartProduct> lineProduct = new ArrayList<FormCartProduct>();
}
