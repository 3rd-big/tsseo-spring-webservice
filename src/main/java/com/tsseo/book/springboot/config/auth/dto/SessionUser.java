package com.tsseo.book.springboot.config.auth.dto;

import com.tsseo.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    // SessionUser에는 인증된 사용자 정보만 필요함. 그 외 정보들은 필요없으므로 name, email, picture만 필드로 선언
    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
