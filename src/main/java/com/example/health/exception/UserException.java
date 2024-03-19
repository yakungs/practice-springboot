package com.example.health.exception;

public class UserException extends BaseException{

    public UserException(String code) {
        super("user : "+code);
    }

    //user register fail
    public static UserException emailNUll(){
        return new UserException("Register email is null");
    }

    public static UserException requestNUll(){
        return new UserException("Request is null");
    }

    //unAuthorize
    public static UserException unAuthorize(){
        return new UserException("user is unAuthorized");
    }

    //User create fail
    public static UserException createEmailNUll(){
        return new UserException("Create email is null");
    }

    public static UserException createPasswordNUll(){
        return new UserException("Create password is null");
    }

    public static UserException createNameNUll(){
        return new UserException("Create name is null");
    }

    public static UserException createEmailDuplicated(){
        return new UserException("Create email is duplication");
    }

    //login fail ไม่ควรบอก user ว่า ไม่มี email นี้
    public static UserException emailNotFound(){
        return new UserException("Login fail");
    }

    public static UserException passwordIncorrect(){
        return new UserException("Login fail");
    }

    public static UserException notFound() {
        return new UserException("User not found");
    }
}
