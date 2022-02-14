package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ConferenceJoinPostReq")
public class ConferenceJoinPostReq {

    public class UserLoginPostReq {
        @ApiModelProperty(name="유저 ID", example="ssafy_web")
        String userId;
        @ApiModelProperty(name="회의 ID", example="idconference")
        String idconference;
    }
}
