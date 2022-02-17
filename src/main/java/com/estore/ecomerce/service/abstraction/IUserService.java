package com.estore.ecomerce.service.abstraction;

import com.estore.ecomerce.domain.User;
import javassist.NotFoundException;

public interface IUserService {
    User getInfoUser() throws NotFoundException;
}
