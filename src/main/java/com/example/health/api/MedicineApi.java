package com.example.health.api;


import com.example.health.businesslogic.MedicineBusiness;
import com.example.health.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicine")
public class MedicineApi {

    private final MedicineBusiness business;

    public MedicineApi(MedicineBusiness business) {
        this.business = business;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMedinceById(@PathVariable("id") String id) throws BaseException {
        String response = business.getMedinceById(id);
        return ResponseEntity.ok(response);
    }

}
