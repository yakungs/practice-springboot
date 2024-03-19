package com.example.health.repository;

import com.example.health.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,String> {

    //ช่วยลดปัญหาการเกิด error ,nullException
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
