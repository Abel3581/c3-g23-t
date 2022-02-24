package com.estore.ecomerce.security.seeder;

import com.estore.ecomerce.domain.*;
import com.estore.ecomerce.repository.*;
import com.estore.ecomerce.security.ApplicationRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDataBaseSeeders {

    private static final String PASSWORD = "tienda1234";
    private static final String HOST_EMAIL = "@test.com";
    private static final String DEFAULT_FIRST_NAME = "Tienda";

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IImageProfileRepository iImageProfileRepository;
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IClientRepository clientRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            createRoles();
        }

        List<Client> users = clientRepository.findAll();
        if (users.isEmpty()) {
            createUsers();
        }
    }

    private void createUsers(ApplicationRole applicationRole) {
        for (int index = 0; index < 10; index++) {
            Client user = new Client();
            user.setUsername(DEFAULT_FIRST_NAME);
            user.setEmail(applicationRole.getName() + index + HOST_EMAIL);
            user.setPassword(passwordEncoder.encode(PASSWORD));
            user.setCity("Garin");
            user.setCountry("Argentina");
            user.setSurname("Acevedo");
            user.setState("state");
            user.setName(applicationRole.getName() + index);

            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName(applicationRole.getFullRoleName()));
            user.setRoles(roles);
            clientRepository.save(user);
        }
    }

    private void createRole(Long id, ApplicationRole applicationRole) {
        Role role = new Role();
        role.setId(id);
        role.setName(applicationRole.getFullRoleName());
        role.setDescription(applicationRole.getName());
        roleRepository.save(role);
    }

    private void createRoles() {
        createRole(1L, ApplicationRole.ADMIN);
        createRole(2L, ApplicationRole.USER);
    }

    private void createUsers() {
        createUsers(ApplicationRole.ADMIN);
        createUsers(ApplicationRole.USER);
    }

}
