package com.example.xing.security;

import com.example.xing.dto.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiexingxing
 * @Created by 2018-11-25 12:34 AM.
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(
            value = "${jwt.route.authentication.path}", method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody RegisterUserDTO registerUserDTO) {
        final String token = authService.login(registerUserDTO.getUsername(), registerUserDTO.getPassword());

        // Return the token
        return ResponseEntity.ok(token);
    }
}
