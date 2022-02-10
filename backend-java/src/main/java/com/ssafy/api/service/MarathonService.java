package com.ssafy.api.service;


import com.ssafy.db.entity.Marathon;

public interface MarathonService {
    int createMarathon(Marathon marathon) throws Exception;
    Marathon getMarathon(String user_id) throws Exception;
}
