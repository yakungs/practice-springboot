package com.example.health.service;

import com.example.health.entity.User;
import com.example.health.exception.BaseException;
import com.example.health.exception.UserException;
import com.example.health.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

//ใช้เพื่อให้ใช้ได้หลาย ๆ ที่
@Service
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    //เหมาะในกรณีที่คิดว่าไม่น่าจะบัค ไม่น่าผิดพลาด
    public User update(User user){
        return repository.save(user);
    }

    public User updateName(String id,String name) throws BaseException {

        Optional<User> opt = repository.findById(id);

        if(opt.isEmpty()){
            throw UserException.notFound();
        }

        User user = opt.get();
        user.setName(name);

        return repository.save(user);
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }

    public boolean matchPassword(String rawPassword,String encodedPassword){
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }

    public Optional<User> findByEmail(String email){

        return repository.findByEmail(email);
    }

    public Optional<User> findById(String id){

        return repository.findById(id);
    }

    public User create(String email,String password,String name) throws BaseException {

        //validate
    if(Objects.isNull(email)){
        //throw error
        throw UserException.createEmailNUll();
    }if(Objects.isNull(password)){
            throw UserException.createPasswordNUll();
    }if(Objects.isNull(name)){
            //throw error
            throw UserException.createNameNUll();
        }

        //verify
        if(repository.existsByEmail(email)){
            throw UserException.createEmailDuplicated();
        }

        //save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);

        return repository.save(entity);
    }
}
