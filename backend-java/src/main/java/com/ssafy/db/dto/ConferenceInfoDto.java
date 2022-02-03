package com.ssafy.db.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class ConferenceInfoDto {
    private int id;
    private String user_id;
    private int book_detail_id;
    private int conference_type_id;
    private String call_start_time;
    private String thumbnail_url;
    private String title;
    private int is_active;
    private String participant;
    private String description;

    public ConferenceInfoDto(int id, String user_id, int book_detail_id, int conference_type_id, String call_start_time, String thumbnail_url, String title, int is_active, String participant, String description) {
        this.id = id;
        this.user_id = user_id;
        this.book_detail_id = book_detail_id;
        this.conference_type_id = conference_type_id;
        this.call_start_time = call_start_time;
        this.thumbnail_url = thumbnail_url;
        this.title = title;
        this.is_active = is_active;
        this.participant = participant;
        this.description = description;
    }
}
