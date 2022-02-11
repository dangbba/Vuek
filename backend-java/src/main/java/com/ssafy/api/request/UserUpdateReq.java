package com.ssafy.api.request;

import com.ssafy.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserUpdateReq {
    private User user;
    private MultipartFile profileImage;
}
