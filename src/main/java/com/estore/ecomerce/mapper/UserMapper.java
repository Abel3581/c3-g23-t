package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.ClientResponse;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserRegisterResponse;
import com.estore.ecomerce.dto.UserUpdateResponse;
import com.estore.ecomerce.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        userRegisterResponse.setId(user.getId());
        userRegisterResponse.setUsername(user.getUsername());
        userRegisterResponse.setEmail(user.getEmail());
        userRegisterResponse.setName(user.getName());
        return userRegisterResponse;
    }

    public void clientEntityRefreshValues(Client client, UserRegisterRequest request) {
        client.setUsername(request.getUsername());
        client.setEmail(request.getEmail());

        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setName(request.getName());
        client.setSurname(request.getSurname());
        client.setCity(request.getCity());
        client.setCountry(request.getCountry());
        client.setState(request.getState());
        ImageProfile imageProfile = fileUploadService.uploadImageProfileToDB(request.getImageProfile());
        client.setImageProfile(imageProfile);

    }

    public UserUpdateResponse userEntity2DtoRefresh(Client entity) {
       UserUpdateResponse dto = new UserUpdateResponse();
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());
        dto.setState(entity.getState());
        dto.setImageProfile(entity.getImageProfile());
        return dto;
    }

    public ClientResponse convertTo(Client client) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getName());
        clientResponse.setUsername(client.getUsername());
        clientResponse.setCity(client.getCity());
        clientResponse.setCountry(client.getCountry());
        clientResponse.setEmail(client.getEmail());
        clientResponse.setPassword(client.getPassword());
        clientResponse.setState(client.getState());
        clientResponse.setSurname(clientResponse.getSurname());

        return clientResponse;
    }
}
