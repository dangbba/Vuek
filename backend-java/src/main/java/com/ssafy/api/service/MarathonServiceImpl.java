package com.ssafy.api.service;

import com.ssafy.db.entity.Marathon;
import com.ssafy.db.repository.MarathonRepository;
import com.ssafy.db.repository.MarathonRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MarathonServiceImpl implements MarathonService {

    @Autowired
    private MarathonRepository marathonRepository;
    @Autowired
    private MarathonRepositorySupport marathonRepositorySupport;

    @Override
    public int createMarathon(Marathon marathon) throws Exception {
        marathonRepository.save(marathon);
        return marathon.getGoalPages();
    }

    @Override
    public Marathon getMarathon(@PathVariable String user_id) throws Exception {
        return marathonRepositorySupport.getMarathon(user_id);
    }

    @Override
    public void updateGoalPages(Marathon marathon, @RequestParam int goalPages){
        marathonRepositorySupport.updateGoalPages(marathon, goalPages);
    }
}
