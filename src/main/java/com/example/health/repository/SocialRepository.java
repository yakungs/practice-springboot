package com.example.health.repository;

import com.example.health.entity.Social;

import com.example.health.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialRepository extends CrudRepository<Social,String> {

    //ช่วยลดปัญหาการเกิด error ,nullException
    Optional<Social> findByUser(User user);

}
