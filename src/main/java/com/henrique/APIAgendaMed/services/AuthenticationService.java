package com.henrique.APIAgendaMed.services;

import com.henrique.APIAgendaMed.dto.AuthDTO;
import com.henrique.APIAgendaMed.dto.LoginDTO;
import com.henrique.APIAgendaMed.dto.SignUpDTO;
import com.henrique.APIAgendaMed.dto.UserDTO;
import com.henrique.APIAgendaMed.exceptions.BadRequestException;
import com.henrique.APIAgendaMed.infra.security.TokenService;
import com.henrique.APIAgendaMed.models.User;
import com.henrique.APIAgendaMed.models.enums.UserRole;
import com.henrique.APIAgendaMed.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthDTO login(LoginDTO data) {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();

        String token = tokenService.generateToken(user);
        return new AuthDTO(user.getId(), user.getName(), token);
    }

    public AuthDTO signUp(SignUpDTO data) {
        if (repository.findByLogin(data.email()) != null)
            throw new BadRequestException("The email is being used by another user");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(null, data.name(), LocalDateTime.now(), data.email(), encryptedPassword, UserRole.USER);

        user = repository.save(user);

        String token = tokenService.generateToken(user);

        return new AuthDTO(user.getId(), user.getName(), token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
