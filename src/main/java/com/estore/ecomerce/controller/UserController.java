package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.dto.ClientResponse;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserUpdateResponse;
import com.estore.ecomerce.service.abstraction.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@Api(value = "User Controller", description = "Crud basic for users")
public class UserController {

    @Autowired
    private IUserService userService;

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleted user", notes = "Return No Content" )
    public ResponseEntity<Void> delete(@PathVariable Long id)throws EntityNotFoundException {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update user", notes = "Return a user updated" )
    public ResponseEntity<UserUpdateResponse> update(@PathVariable Long id, @ModelAttribute UserRegisterRequest request) throws NotFoundException {
        Client client = (Client) userService.getInfoUser();
        UserUpdateResponse result = userService.update(client, id, request);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by id", notes = "Return a user by id" )
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id){
         ClientResponse response = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    @ApiOperation(value = "Get info user", notes = "Return a user info" )
    public ResponseEntity<ClientResponse> getMyClient() throws NotFoundException{
        Client client = (Client) userService.getInfoUser();
        ClientResponse response = userService.getById(client.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
