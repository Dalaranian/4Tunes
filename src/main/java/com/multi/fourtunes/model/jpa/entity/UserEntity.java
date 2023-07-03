package com.multi.fourtunes.model.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO", nullable = false, unique = true)
    private Integer userNo;

    @Column(name = "USER_ID", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "USER_NAME", nullable = false, length = 20)
    private String userName;

    @Column(name = "USER_PW", nullable = false, columnDefinition = "TEXT")
    private String userPw;

    @Column(name = "USER_GRADE", nullable = false, length = 50)
    private String userGrade;

    @Column(name = "USER_SUGGESTCOUNT", nullable = false)
    private Integer userSuggestCount;

}
