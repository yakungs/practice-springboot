package com.example.health.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.health.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    //ควรเปลี่ยนทุห3-4เดือน กันการสุ่มโทเค่น
    //ควรที่ฝั่งหลังบ้าน ไม่ควรส่งให้หน้าบ้าน
    @Value("${app.token.secret}")
    private String secret;

    //ใครสร้าง โทเค่น
    @Value("${app.token.issuer}")
    private String issuer;

    public String generateToken(User user){

        //ควรมี expire ยิ่งน้องยิ่งดี ป้องกันการเอาโทเค่นไปใช้ซ้ำ
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,60);
        Date expireAt = calendar.getTime();

        Date now = new Date();

        //principle เป็น term ของ springSecurity

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("principal", user.getId())
                .withClaim("role", "USER")
                .withExpiresAt(expireAt)
                .sign(Algorithm());

    }

    public DecodedJWT verifyToken(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm())
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
            verifier.verify(token);
            return verifier.verify(token);
        }catch (Exception e){
            return null;
        }
    }

    private Algorithm Algorithm(){
        return  Algorithm.HMAC256("secret");
    }

}
