package com.estore.ecomerce.integration.user;

import com.estore.ecomerce.common.AbstractBaseIntegrationTest;
import com.estore.ecomerce.common.JwtUtil;
import com.estore.ecomerce.common.SecurityTestConfig;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.User;
import com.estore.ecomerce.dto.ErrorResponse;
import com.estore.ecomerce.dto.UserAuthenticatedRequest;
import com.estore.ecomerce.dto.UserAuthenticatedResponse;
import com.estore.ecomerce.repository.IClientRepository;
import com.estore.ecomerce.security.ApplicationRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginUserIntegrationTest extends AbstractBaseIntegrationTest{

    private static final String PATH = "/auth/login";
    private String token = SecurityTestConfig.createToken(
            "johnny@gmail.com",
            ApplicationRole.USER.getFullRoleName());
    @Autowired
    IClientRepository repository;
    @MockBean
    private JwtUtil jwtUtils;

    @Test
    public void shouldLoginUserSuccessfully() {
        Client user = stubUser(ApplicationRole.USER.getFullRoleName());
        when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(user);
        UserAuthenticatedRequest userAuthenticationRequest = getUserAuthenticationRequest();
        when(jwtUtils.generateToken(user)).thenReturn(token);

        ResponseEntity<UserAuthenticatedResponse> response = restTemplate.exchange(
                createURLWithPort(PATH),
                HttpMethod.POST,
                new HttpEntity<>(userAuthenticationRequest, headers),
                UserAuthenticatedResponse.class);

        assertNotNull(response.getBody().getToken());
        assertEquals(response.getBody().getEmail(), user.getEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    private UserAuthenticatedRequest getUserAuthenticationRequest() {
        UserAuthenticatedRequest userAuthenticationRequest = new UserAuthenticatedRequest();
        userAuthenticationRequest.setEmail("johnny@gmail.com");
        userAuthenticationRequest.setPassword("123456789");
        return userAuthenticationRequest;
    }
}
