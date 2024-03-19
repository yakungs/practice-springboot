package com.example.health.exception;

public class FileException extends BaseException{


    public FileException(String code) {
        super("File : "+code);
    }

    public static FileException isNUll(){
        return new FileException("is null");
    }

    public static FileException fileMaxSize(){
        return  new FileException("max size");
    }

    public static FileException unsupported(){
        return new FileException("unsupported file type");
    }

}
