package com.example.health.businesslogic;

import com.example.health.exception.BaseException;
import com.example.health.exception.EmailException;
import com.example.health.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailBusiness {

    private final EmailService emailService;

    public EmailBusiness(EmailService emailService) {
        this.emailService = emailService;
    }


    public void sendActivateUserEmail(String email,String name,String token) throws BaseException {

        //prepare content(HTML)
        String html;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
                throw EmailException.templateNotFound();
        }

        String finalLink = "http://localhost:4200/activate/"+token;
        html = html.replace("${NAME}",name);
        html = html.replace("${LINK}",finalLink);

        //prepare subject
        String subject = "Please activate your account";

        emailService.send(email,subject,html);

    }

    private String readEmailTemplate(String filename) throws IOException {

        File file = ResourceUtils.getFile("classpath:email/" + filename);
        return FileCopyUtils.copyToString(new FileReader(file));

    }

}
