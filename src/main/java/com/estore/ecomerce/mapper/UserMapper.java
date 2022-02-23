package com.estore.ecomerce.mapper;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserRegisterResponse;
import com.estore.ecomerce.dto.UserUpdateResponse;
import com.estore.ecomerce.service.FileUploadService;
import com.estore.ecomerce.service.abstraction.IImageProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UserMapper {

    @Autowired
    private IImageProfileService service;

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
        userRegisterResponse.setUsername(user.getUsername());
        userRegisterResponse.setId(user.getId());
        userRegisterResponse.setEmail(user.getEmail());
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
}
