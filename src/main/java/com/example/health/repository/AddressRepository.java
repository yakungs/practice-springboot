package com.example.health.repository;

import com.example.health.entity.Address;


import com.example.health.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address,String> {

    //ช่วยลดปัญหาการเกิด error ,nullException
    List<Address> findByUser(User user);

}
