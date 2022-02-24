package com.estore.ecomerce.service.abstraction;

import com.estore.ecomerce.dto.UserAuthenticatedRequest;
import com.estore.ecomerce.dto.UserAuthenticatedResponse;

public interface IAuthenticationService {
    UserAuthenticatedResponse authentication(UserAuthenticatedRequest request);
}
