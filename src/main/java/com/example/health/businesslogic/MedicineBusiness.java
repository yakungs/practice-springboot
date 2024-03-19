package com.example.health.businesslogic;

import com.example.health.exception.BaseException;
import com.example.health.exception.MedicineException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MedicineBusiness {

    public String getMedinceById(String id) throws BaseException {
        if(Objects.equals("1234",id)){
            throw MedicineException.notfound();
        }
        return id;
    }



}
