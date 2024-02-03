package com.henrique.APIAgendaMed.services;

import com.henrique.APIAgendaMed.dto.UserDTO;
import com.henrique.APIAgendaMed.exceptions.NotFoundException;
import com.henrique.APIAgendaMed.models.User;
import com.henrique.APIAgendaMed.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserDTO> findAll() {
        List<User> list = repository.findAll();
        List<UserDTO> listDTO = new ArrayList<>();

        for (User u : list) {
            listDTO.add(new UserDTO(u.getId(), u.getName(), u.getCreatedAt()));
        }

        return listDTO;
    }

    public UserDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserDTO(user.getId(), user.getName(), user.getCreatedAt());
    }
}
