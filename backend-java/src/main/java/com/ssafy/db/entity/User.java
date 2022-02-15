package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.openvidu.java.client.OpenViduRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@Embeddable
public class User implements Serializable {

    @Id
    @Column(name = "userId")
    String userId;

    @Column(name = "userName")
    String userName;
    String email;
    String genre;
    String goal;
    String socialLink;
    String profileImage;
//    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @PrePersist
    public void prePersist(){
        this.genre = this.genre == null ? "없음" : this.genre;
        this.goal = this.goal == null ? "없음" : this.goal;
    }

}
