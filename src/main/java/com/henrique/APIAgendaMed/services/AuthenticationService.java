package com.henrique.APIAgendaMed.services;

import com.henrique.APIAgendaMed.dto.LoginDTO;
import com.henrique.APIAgendaMed.dto.SignUpDTO;
import com.henrique.APIAgendaMed.dto.UserDTO;
import com.henrique.APIAgendaMed.exceptions.BadRequestException;
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

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    public void login(LoginDTO data) {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        authenticationManager.authenticate(usernamePassword);
    }

    public UserDTO signUp(SignUpDTO data) {
        if (repository.findByLogin(data.email()) != null)
            throw new BadRequestException("The email is being used by another user");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(null, data.name(), LocalDateTime.now(), data.email(), encryptedPassword, UserRole.USER);

        user = repository.save(user);

        return new UserDTO(user.getId(), user.getName(), user.getCreatedAt());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
