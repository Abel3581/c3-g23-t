package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.Role;
import com.estore.ecomerce.repository.IRoleRepository;
import com.estore.ecomerce.service.abstraction.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role findBy(String name) {
        return roleRepository.findByName(name);
    }
}
