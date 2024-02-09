package com.henrique.APIAgendaMed.repositories;

import com.henrique.APIAgendaMed.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
