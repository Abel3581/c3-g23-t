package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.Role;

public interface IRoleService {
    Role findBy(String name);
}
