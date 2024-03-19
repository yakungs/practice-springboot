package com.example.health.exception;

public class EmailException extends BaseException{

    public EmailException(String code) {
        super("email : "+code);
    }
    public static EmailException templateNotFound(){
        return new EmailException("is null");
    }


}
