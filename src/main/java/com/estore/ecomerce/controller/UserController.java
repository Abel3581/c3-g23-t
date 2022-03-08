package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.ClientResponse;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserUpdateResponse;
import com.estore.ecomerce.service.abstraction.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)throws EntityNotFoundException {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> update(@PathVariable Long id, @ModelAttribute UserRegisterRequest request) throws NotFoundException {
        UserUpdateResponse result = userService.update(id, request);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id){
         ClientResponse response = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ClientResponse> getMyClient() throws NotFoundException{
        Client client = (Client) userService.getInfoUser();
        ClientResponse response = userService.getById(client.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
