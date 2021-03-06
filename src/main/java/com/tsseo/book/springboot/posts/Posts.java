package com.tsseo.book.springboot.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
/**
 *  @Entity
 *  테이블과 링크될 클래스임을 나타냄
 */
@Entity
public class Posts extends BaseTimeEntity{

    // @Id 해당 테이블의 PK 필드를 나타냄
    @Id
    // @GeneratedValue PK의 생성규칙을 나타냄, 스프링 부트 2.0 에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 설정 됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  @Column
     *  테이블의 컬럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 됨
     *  사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
     *  문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나,
     *  타입을 TEXT로 변경하고 싶거나 등의 경우에 사용
     */
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
