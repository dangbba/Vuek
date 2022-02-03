package com.ssafy.db.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDto {
    private String Conferenceid;
    private String user_name;
    private String content;

    public String getWriter() {
        return user_name;
    }
    public String getMessage(String string) {
        return string;

    }
    public String getConferenceId() {
        return Conferenceid;
    }
}