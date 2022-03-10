package com.estore.ecomerce.domain;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
@ApiModel("Model User")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "the user's", required = true)
    private Long id;

    @Column(name = "username", nullable = false, updatable = true)
    @ApiModelProperty(value = "the user's username", required = true)
    private String username;

    @Column(name = "password", nullable = false, updatable = true)
    @ApiModelProperty(value = "the user's password", required = true)
    private String password;

    @Column(name = "email", nullable = false, updatable = true)
    @ApiModelProperty(value = "the user's email", required = true)
    private String email;

    @CreationTimestamp
    private Timestamp timestamp;

    @Column(name = "soft_deleted")
    private boolean softDeleted = Boolean.FALSE;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @Column(name = "roles_id")
    private List<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.softDeleted;
    }

}

