package com.estore.ecomerce.service;

import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserRegisterResponse;

public interface IRegisterUserService {

    UserRegisterResponse register(UserRegisterRequest request);
}
