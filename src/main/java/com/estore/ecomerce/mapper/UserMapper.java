package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserRegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public Client userDto2Entity(UserRegisterRequest request) {
        Client user = new Client();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setEmail(request.getEmail());
        user.setSurname(request.getSurname());
        user.setState(request.getState());
        user.setPassword(request.getPassword());
        return user;
    }

    public UserRegisterResponse userEntity2Dto(Client user) {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setUsername(user.getUsername());
        userRegisterResponse.setId(user.getId());
        userRegisterResponse.setEmail(user.getEmail());
        return userRegisterResponse;
    }
}
