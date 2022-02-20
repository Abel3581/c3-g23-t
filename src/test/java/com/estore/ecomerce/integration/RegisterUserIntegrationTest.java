package com.estore.ecomerce.integration;


import com.estore.ecomerce.common.AbstractBaseIntegrationTest;
import com.estore.ecomerce.common.JwtUtil;
import com.estore.ecomerce.common.SecurityTestConfig;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserRegisterResponse;
import com.estore.ecomerce.security.ApplicationRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterUserIntegrationTest extends AbstractBaseIntegrationTest {

    private static final String PATH = "/auth/register";
    private String token = SecurityTestConfig.createToken(
            "johnny@gmail.com",
            ApplicationRole.USER.getFullRoleName());


    @MockBean
    private JwtUtil jwtUtil;

    // Debería registrarse un usuario y su token con éxito
    @Test
    public void shouldRegisterUserWithTokenSuccessfully()
    {
        Client user = stubUser(ApplicationRole.USER.getFullRoleName());
        when(userRepository.findByEmail(eq("johnny@gmail.com"))).thenReturn(null);
        when(userRepository.save(isA(Client.class))).thenReturn(user);
        when(jwtUtil.generateToken(eq(user))).thenReturn(token);

        UserRegisterRequest userDetailsRequest = getUserDetailsRequest();

        ResponseEntity<UserRegisterResponse> response = restTemplate.exchange(
                createURLWithPort(PATH),
                HttpMethod.POST,
                new HttpEntity<>(userDetailsRequest, headers),
                UserRegisterResponse.class);

        assertNotNull(response.getBody().getToken());
        assertEquals(response.getBody().getEmail(), user.getEmail());
        assertEquals(response.getBody().getUsername(), user.getUsername());
        assertEquals(response.getBody().getName(), user.getName());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private UserRegisterRequest getUserDetailsRequest() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setEmail("johnny@gmail.com");
        request.setUsername("John");
        request.setPassword("123456789");
        request.setName("Abel");
        return request;
    }

}
