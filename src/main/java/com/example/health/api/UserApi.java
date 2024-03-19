package com.example.health.api;


import com.example.health.businesslogic.UserBusiness;
import com.example.health.entity.User;
import com.example.health.exception.BaseException;
import com.example.health.model.HealthResponse;
import com.example.health.model.MLoginRequest;
import com.example.health.model.MRegisterRequest;
import com.example.health.model.MRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserApi {

    //Method 1 field injection
//    Autowired << Dependency injection (DI) ง่ายต่อการ Test
//    private HealthBusiness business;

    //Method 2 constructor เร็วกว่าด้าน Performance
    private final UserBusiness business;

    public UserApi(UserBusiness business) {
        this.business = business;
    }


    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<MRegisterResponse> register(@RequestBody MRegisterRequest req) throws BaseException {

        MRegisterResponse response = business.register(req);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = business.refreshToken();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("upload")
    public ResponseEntity<String> uploadHealthCer(@RequestPart MultipartFile file) throws BaseException {

        String response = business.upload(file);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MLoginRequest request) throws BaseException {

        String  response = business.login(request);

        return ResponseEntity.ok(response);
    }

}
