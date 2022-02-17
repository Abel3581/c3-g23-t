package com.estore.ecomerce.service;

import com.estore.ecomerce.common.JwtUtil;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.Role;
import com.estore.ecomerce.domain.User;
import com.estore.ecomerce.dto.UserAuthenticatedRequest;
import com.estore.ecomerce.dto.UserAuthenticatedResponse;
import com.estore.ecomerce.dto.UserRegisterRequest;
import com.estore.ecomerce.dto.UserRegisterResponse;
import com.estore.ecomerce.mapper.UserMapper;
import com.estore.ecomerce.repository.IClientRepository;
import com.estore.ecomerce.repository.IUserRepository;
import com.estore.ecomerce.security.ApplicationRole;
import com.estore.ecomerce.service.abstraction.IAuthenticationService;
import com.estore.ecomerce.service.abstraction.IRegisterUserService;
import com.estore.ecomerce.service.abstraction.IRoleService;
import com.estore.ecomerce.service.abstraction.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, IRegisterUserService, IAuthenticationService, IUserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found.";
    private static final String USER_EMAIL_ERROR = "Email address is already used.";

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()) != null){
            throw new RuntimeException(USER_EMAIL_ERROR);
        }
        Client user = userMapper.userDto2Entity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findBy(ApplicationRole.USER.getFullRoleName()));
        user.setRoles(roles);
        Client userCreate = clientRepository.save(user);
        UserRegisterResponse userRegisterResponse = userMapper.userEntity2Dto(userCreate);
        userRegisterResponse.setToken(jwtUtil.generateToken(userCreate));
        return userRegisterResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }

    private Client getUser(Long id) {
        Optional<Client> userOptional = clientRepository.findById(id);
        if (userOptional.isEmpty() || userOptional.get().isSoftDeleted()) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }
        return userOptional.get();
    }

    private Client getUser(String username) {
        Client user = clientRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE);
        }
        return user;
    }

    @Override
    public UserAuthenticatedResponse authentication(UserAuthenticatedRequest request) {
        User user = getUser(request.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        return new UserAuthenticatedResponse(jwtUtil.generateToken(user), user.getEmail());
    }


    @Override
    public User getInfoUser() throws NotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            String username = ((User)principal).getUsername();
        }else{
            String username = principal.toString();
        }
        return userRepository.findByEmail(principal.toString());
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        Client user = getUser(id);
        user.setSoftDeleted(true);
        clientRepository.save(user);
    }
}
