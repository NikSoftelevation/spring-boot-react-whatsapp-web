package com.springbootwhatspp.controller;

import com.springbootwhatspp.exception.UserException;
import com.springbootwhatspp.jwt.TokenProvider;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.response.AuthResponse;
import com.springbootwhatspp.request.LoginRequest;
import com.springbootwhatspp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {

        String email = user.getEmail();
        String full_name = user.getFull_name();
        String password = user.getPassword();

        User isUser = userRepository.findByEmail(email);

        if (isUser != null) {
            throw new UserException("Email is already used with another account " + email);
        }
        User createdUser = new User();
        createdUser.setFull_name(full_name);
        createdUser.setEmail(email);
        createdUser.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwtToken = tokenProvider.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponse(jwtToken, true), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest loginRequest) {


        Authentication authentication = doAuthenticate(loginRequest.getEmail(), loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = tokenProvider.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(jwtToken, true), HttpStatus.OK);
    }

    public Authentication doAuthenticate(String username, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username " + username);
        }
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password or username" + password);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}