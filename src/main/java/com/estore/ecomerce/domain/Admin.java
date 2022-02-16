package com.estore.ecomerce.domain;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User{
    User user;

    @Override
    public String getUsername() {
        return super.getUsername(); 
    }
    
}
