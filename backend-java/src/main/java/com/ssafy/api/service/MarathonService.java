package com.ssafy.api.service;


import com.ssafy.db.entity.Marathon;
import org.springframework.web.bind.annotation.RequestParam;

public interface MarathonService {
    int createMarathon(Marathon marathon) throws Exception;
    Marathon getMarathon(String user_id) throws Exception;
    void updateGoalPages(Marathon marathon, @RequestParam int goalPages) throws Exception;
}
