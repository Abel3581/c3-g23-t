package com.estore.ecomerce.dto;

import com.estore.ecomerce.domain.Cart;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "El nombre de usuario no puede estar vacío ni ser nulo")
    private String username;

    @NotBlank (message = "El email no puede estar vacío")
    private String email;

    @NotBlank (message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 25, message = "La contraseña debe ser entre 6 y 25 caracteres")
    private String password;

    @NotBlank(message = "El nombre no puede estar vacío ni ser nulo")
    @Pattern(regexp = "[a-zA-Z]+", message = "El nombre no puede contener números")
    private String name;

    @Column(name = "surname", nullable = false, updatable= true)
    private String surname;

    @Column(name = "city", nullable = false, updatable = true)
    private String city;

    @Column(name = "country", nullable = false, updatable = true)
    private String country;

    @Column(name = "state", nullable = false, updatable = true)
    private String state;



}
