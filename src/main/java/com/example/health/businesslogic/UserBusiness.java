package com.example.health.businesslogic;


import com.example.health.entity.User;
import com.example.health.exception.BaseException;
import com.example.health.exception.FileException;
import com.example.health.exception.UserException;
import com.example.health.mapper.UserMapper;
import com.example.health.model.MLoginRequest;
import com.example.health.model.MRegisterRequest;
import com.example.health.model.MRegisterResponse;
import com.example.health.service.TokenService;
import com.example.health.service.UserService;
import com.example.health.utils.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public UserBusiness(UserService service, UserMapper userMapper, TokenService tokenService) {
        this.userService= service;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public MRegisterResponse register(MRegisterRequest req) throws BaseException {

        User user = userService.create(req.getEmail(),req.getPassword(),req.getName());

        System.out.println(user);
        //TODO : MAPPER

        return userMapper.toRegisterResponse(user);
    }

    public String upload(MultipartFile file) throws BaseException {
        //validate file
        if(file == null){
            throw FileException.isNUll();
        }
        if(file.getSize() > 1048576* 2){
            throw FileException.fileMaxSize();
        }

        String contentType = file.getContentType();

        if(contentType == null){
            //throw error
        }
        List<String> supportType = Arrays.asList("image/jpeg","image/png");
        if(!supportType.contains(contentType)){
            //throw error (unsupported)
            throw FileException.unsupported();
        }

        //TODO: upload file File storage(AWS,S3,etc...)
        try {
            byte[] bytes  = file.getBytes();
            System.out.println("Hello : "+ file.getContentType()+", "+file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Hello";
    }

    public String refreshToken() throws BaseException {
        //เช็คว่าใครล้อคอินอยู่
        Optional<String> opt = SecurityUtil.currentUserId();
        if(opt.isEmpty()){
            throw UserException.unAuthorize();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if(optUser.isEmpty()){
            throw UserException.notFound();
        }
        User user = optUser.get();
        return tokenService.generateToken(user);
    }

    public String login(MLoginRequest req) throws BaseException {

        //validate request

        Optional<User> opt = userService.findByEmail(req.getEmail());

        if(opt.isEmpty()){
            throw UserException.emailNotFound();
        }
        User user = opt.get();

        if(!userService.matchPassword(req.getPassword(), user.getPassword())){
            throw UserException.passwordIncorrect();
        }

        //TODO: generate:JWT


        return tokenService.generateToken(user);


    }

}
