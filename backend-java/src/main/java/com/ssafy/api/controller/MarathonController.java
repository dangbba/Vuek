package com.ssafy.api.controller;

import com.ssafy.api.service.MarathonService;
import com.ssafy.db.entity.Marathon;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/v1/marathon")
public class MarathonController {
    public static final Logger logger = LoggerFactory.getLogger(MarathonController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private MarathonService marathonService;

    @ApiOperation(value = "마라톤을 생성한다. 그리고 DB저장 여부에 따라 성공/실패를 반환한다.", response = String.class)
    @PostMapping("/create")
    public ResponseEntity<String> createConference(@RequestBody Marathon marathon) throws Exception {
        marathonService.createMarathon(marathon);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "nowPages를 조회한다", response = String.class)
    @GetMapping("/nowPages/{user_id}")
    public ResponseEntity<Integer> getNowPages(@PathVariable String user_id) throws Exception {
        int nowpages = marathonService.getNowPages(user_id);
        return new ResponseEntity<Integer>(nowpages, HttpStatus.OK);
    }


}
