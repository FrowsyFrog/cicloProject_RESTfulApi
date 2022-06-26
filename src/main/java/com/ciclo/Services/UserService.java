package com.ciclo.Services;
import com.ciclo.Dto.LoginRequest;
import com.ciclo.Dto.LoginResponse;
import com.ciclo.Entities.ERole;
import com.ciclo.Entities.Role;
import com.ciclo.Repositories.RoleRepository;
import com.ciclo.Security.Services.UserPrincipal;
import com.ciclo.Security.jwt.JwtUtils;
import com.ciclo.Util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ciclo.Dto.UserRequest;
import com.ciclo.Entities.User;
import com.ciclo.Repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;

    public UserService(UserRepository repository) { this.userRepository = repository; }

    @Transactional
    public User createUser(UserRequest userRequest) {
        UserValidator.validateCreate(userRequest);
        User userNew = userRepository.getUserByEmail(userRequest.getEmail());
        UserValidator.validateUserEmail(userNew);
        userNew = initUser(userRequest);
        return userRepository.save(userNew);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() { return userRepository.getAllUsers(); }

    @Transactional(readOnly = true)
    public User getUserById(Long idUser) { return userRepository.getUserById(idUser); }
    @Transactional(readOnly = true)
    public User getUserByLogIn(String email, String password) {
        User userNew = userRepository.getUserByLogIn(email, password);
        UserValidator.validateLogIn(userNew);
        return userNew;
    }

    private User initUser(UserRequest userRequest) {
        User userObj = new User();
        userObj.setUsername(userRequest.getUsername());
        userObj.setEmail(userRequest.getEmail());
        userObj.setImageurl(userRequest.getImageurl());
        userObj.setPassword(userRequest.getPassword());
        Set<String> strRoles = userRequest.getRole();
        Set<Role> roles = new HashSet<>();

        /*if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: No se encuentra el rol."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: No se encuentra el rol."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: No se encuentra el rol"));

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: No se encuentra el rol."));
                        roles.add(userRole);
                }
            });
        }*/

        userObj.setRoles(roles);


        return userObj;
    }

    public LoginResponse authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
