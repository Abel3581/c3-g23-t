package com.estore.ecomerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter @Setter @NoArgsConstructor
public class UserAuthenticatedResponse {

    private String token;
    private String email;
    private Collection<? extends GrantedAuthority> roleName;



    public UserAuthenticatedResponse(String token, String email, Collection<? extends GrantedAuthority> roleName) {
        this.token= token;
        this.email = email;
        this.roleName = roleName;
    }
}
