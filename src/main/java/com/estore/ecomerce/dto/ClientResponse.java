package com.estore.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String city;
    private String country;
    private String state;

}
