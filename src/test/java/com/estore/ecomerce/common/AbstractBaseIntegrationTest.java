package com.estore.ecomerce.common;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.Role;
import com.estore.ecomerce.domain.User;
import com.estore.ecomerce.repository.IClientRepository;
import com.estore.ecomerce.repository.IUserRepository;
import com.estore.ecomerce.service.abstraction.IRoleService;
import org.assertj.core.util.Lists;
import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;

import java.sql.Timestamp;
import java.time.Instant;

public abstract class AbstractBaseIntegrationTest {

    protected static final long USER_ID = 1L;
    protected TestRestTemplate restTemplate = new TestRestTemplate();
    protected HttpHeaders headers = new HttpHeaders();

    @MockBean
    protected IClientRepository userRepository;
    @MockBean
    protected AuthenticationManager authenticationManager;

    @MockBean
    protected IRoleService roleService;

    @LocalServerPort
    private int port;

    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    protected void setAuthorizationHeaderBasedOn(String role) {
        String jwt = SecurityTestConfig.createToken("johnny@gmail.com", role);
        headers.set("Authorization", jwt);
    }

    protected Role stubRole(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }


    protected Client stubUser(String role){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new Client(
                USER_ID,
                "John",
                "123456789",
                "johnny@gmail.com",
                Timestamp.from(Instant.now()),
                false,
                Lists.list(stubRole(role)),
                "Abel"
        );
    }

}
