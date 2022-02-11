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

    @ApiOperation(value = "마라톤을 조회한다", response = String.class)
    @GetMapping("/marathon-info/{user_id}")
    public ResponseEntity<Marathon> getMarathon(@PathVariable String user_id) throws Exception {
        Marathon marathon = marathonService.getMarathon(user_id);
        return new ResponseEntity<Marathon>(marathon, HttpStatus.OK);
    }

    @ApiOperation(value = "goalpages를 수정한다", response = String.class)
    @PutMapping("/updateGoalpages")
    public ResponseEntity<String> updateGoalPages(Marathon marathon, @RequestParam int goalpages) throws Exception {
        marathonService.updateGoalPages(marathon, goalpages);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

}
