package com.tsseo.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 *  @EnableJpaAuditing
 *  JPA Auditing 어노테이션들을 모두 활성화할 수 있게끔 함
 */
/*  221p
@EnableJpaAuditing*/
/**
 *  @SpringBootApplication
 *  해당 어노테이션으로 인해 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정됨
 *  해당 파일이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치해야만 함
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run으로 인해 내장 WAS를 실행
        SpringApplication.run(Application.class, args);
    }
}
