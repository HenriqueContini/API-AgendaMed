package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
