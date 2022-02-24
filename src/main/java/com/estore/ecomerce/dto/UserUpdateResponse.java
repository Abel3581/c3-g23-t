package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.ImageProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateResponse {


    private String username;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String city;
    private String country;
    private String state;
    private ImageProfile imageProfile;
}
