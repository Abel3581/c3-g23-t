package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImageProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {

    private Long id;
    private String username;
    private String email;
    private String token;
    private String name;


}
