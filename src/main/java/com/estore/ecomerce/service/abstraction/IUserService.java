package com.estore.ecomerce.service.abstraction;

import com.estore.ecomerce.domain.User;
import javassist.NotFoundException;

import javax.persistence.EntityNotFoundException;

public interface IUserService {
    User getInfoUser() throws NotFoundException;

    void delete(Long id)throws EntityNotFoundException;
}
