package com.example.health.exception;

public class MedicineException extends BaseException{


    public MedicineException(String code) {
        super("Medicine : "+ code);
    }

    public static MedicineException notfound(){
        return new MedicineException("not found");
    }
}
