package com.henrique.APIAgendaMed.controllers;

import com.henrique.APIAgendaMed.dto.AuthDTO;
import com.henrique.APIAgendaMed.dto.LoginDTO;
import com.henrique.APIAgendaMed.dto.SignUpDTO;
import com.henrique.APIAgendaMed.dto.UserDTO;
import com.henrique.APIAgendaMed.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginDTO data) {
        return ResponseEntity.ok().body(service.login(data));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthDTO> signup(@RequestBody @Valid SignUpDTO data) {
        AuthDTO user = service.signUp(data);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
