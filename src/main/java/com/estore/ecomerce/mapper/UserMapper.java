package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserRegisterResponse;
import com.estore.ecomerce.service.abstraction.IImageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private IImageProfileService service;

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
        user.setImageProfile(service.getImageProfile(request.getImageProfileId()));
        return user;
    }

    public UserRegisterResponse userEntity2Dto(Client user) {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setUsername(user.getUsername());
        userRegisterResponse.setId(user.getId());
        userRegisterResponse.setEmail(user.getEmail());
        userRegisterResponse.setImageProfile(user.getImageProfile());
        return userRegisterResponse;
    }
}
