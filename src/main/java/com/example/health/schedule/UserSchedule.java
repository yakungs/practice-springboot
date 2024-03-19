package com.example.health.schedule;

import com.example.health.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserSchedule {

    private final UserService userService;

    public UserSchedule(UserService userService) {
        this.userService = userService;
    }

    // Schedule Note (24 hours)
    //1 => second
    //2 => minutes
    //3 => hours
    //4 => days
    //5 => month
    //6 => year

    //every minutes (Thai)
    @Scheduled(cron = "0 * * * * * ",zone = "Asia/Bangkok")
    public void testEveryMinutes(){
        log.info("Hello, What's up");
    }

    //every night (UTC)
    @Scheduled(cron = "0 0 0 * * * ")
    public void EveryMidNight(){



    }

}
