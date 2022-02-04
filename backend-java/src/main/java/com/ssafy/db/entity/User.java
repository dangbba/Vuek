package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    String userId;

    @Column(name = "user_name")
    String username;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;



}
