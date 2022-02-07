package com.ssafy.db.dto;

import com.ssafy.db.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString
@Data
public class ConferenceInfoDto {
    private int id;
    private String userId;
    private int bookDetailId;
    private int conferenceTypeId;
    private String callStartTime;
    private String thumbnailUrl;
    private String title;
    private int isActive;
    private Collection<User> participant;
    private String description;

    public ConferenceInfoDto(int id, String userId, int bookDetailId, int conferenceTypeId, String callStartTime, String thumbnailUrl, String title, int isActive, Collection<User> participant, String description) {
        this.id = id;
        this.userId = userId;
        this.bookDetailId = bookDetailId;
        this.conferenceTypeId = conferenceTypeId;
        this.callStartTime = callStartTime;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.isActive = isActive;
        this.participant = participant;
        this.description = description;
    }
}
