package com.example.health.service;

import com.example.health.entity.Address;
import com.example.health.entity.Social;
import com.example.health.entity.User;
import com.example.health.repository.AddressRepository;
import org.springframework.stereotype.Service;
import java.util.List;

//ใช้เพื่อให้ใช้ได้หลาย ๆ ที่
@Service
public class AddressService {

    private final AddressRepository repository;


    public AddressService(AddressRepository repository) {
        this.repository = repository;

    }

    public List<Address> findByUser(User user){

        return repository.findByUser(user);
    }

    public Address createAddress(User user,String line1,String line2, String zipcode){

        Address entity = new Address();

        entity.setUser(user);

        entity.setLine1(line1);

        entity.setLine2(line2);

        entity.setZipcode(zipcode);

        return repository.save(entity);
    }

}
