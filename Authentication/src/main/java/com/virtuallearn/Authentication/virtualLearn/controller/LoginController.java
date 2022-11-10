package com.virtuallearn.Authentication.virtualLearn.controller;


import com.virtuallearn.Authentication.virtualLearn.model.JwtRequest;
import com.virtuallearn.Authentication.virtualLearn.model.JwtResponse;
import com.virtuallearn.Authentication.virtualLearn.service.MyUserDetailsService;
import com.virtuallearn.Authentication.virtualLearn.utility.JwtUtility;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private MyUserDetailsService myUserDetailsService;


    @PutMapping("/Login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        }
        catch (DisabledException e){
            throw new Exception("User Disabled");
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUserName());
        final String token = jwtUtility.generateToken(userDetails);
        System.out.println("Inside Login");
        return new JwtResponse(token);
    }

    @GetMapping("/refreshToken")
    public JwtResponse refreshToken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
        System.out.println("Inside refresh Token");
        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtUtility.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return new JwtResponse(token);
    }
    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

    @GetMapping("/message")
    public String getMessage(){
        return "Hello how are you";
    }




}
