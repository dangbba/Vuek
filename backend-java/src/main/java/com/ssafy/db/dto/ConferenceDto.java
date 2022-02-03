package com.ssafy.db.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class ConferenceDto {
    private Long id;
    private String user_id;
    private int book_detail_id;
    private int conference_type;
    private LocalDateTime callStartTime;
    private LocalDateTime callEndTime;
    private String thumbnail_url;
    private String title;
    private int is_active;

    private int empty;
    private int first;
    private int last;
    private int number;
    private int numberOfElements;

    private int offset;
    private int pageNumber;
    private int pageSize;
    private int paged;
    private int unpaged;

    private int size;
    private int totalElements;
    private int totalPages;
}
