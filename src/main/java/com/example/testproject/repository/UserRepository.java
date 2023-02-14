package com.example.testproject.repository;

import com.example.testproject.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByName(String username);
    boolean existsByName(String userName);
}
