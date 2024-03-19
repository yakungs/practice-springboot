package com.example.health.service;

import com.example.health.entity.Social;
import com.example.health.entity.User;
import com.example.health.repository.SocialRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

//ใช้เพื่อให้ใช้ได้หลาย ๆ ที่
@Service
public class SocialService {

    private final SocialRepository repository;


    public SocialService(SocialRepository repository) {
        this.repository = repository;

    }

    public Optional<Social> findByUser(User user){

        return repository.findByUser(user);
    }

    public Social createSocial(User user,String facebook,String line, String instagram,String tiktok){
        Social entity = new Social();

        entity.setUser(user);

        entity.setFacebook(facebook);

        entity.setLine(line);

        entity.setInstagram(instagram);

        entity.setTiktok(tiktok);

        return repository.save(entity);
    }

}
