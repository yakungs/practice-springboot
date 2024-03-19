package com.example.health;

import com.example.health.businesslogic.EmailBusiness;

import com.example.health.exception.BaseException;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmailBusiness {

    @Autowired
    private EmailBusiness emailBusiness;

    @Order(1)
    @Test
    void test() throws BaseException {

        emailBusiness.sendActivateUserEmail(
                TestEmailData.email,
                TestEmailData.name,
                TestEmailData.token
        );

    }


    interface TestEmailData{

        String email = "kazuya2komatsu@gmail.com";

        String name = "Kazuya";

        String token = "e5dhr@$sa8321151d%sa";

    }

}
