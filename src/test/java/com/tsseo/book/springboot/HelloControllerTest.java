package com.tsseo.book.springboot;

import com.tsseo.book.springboot.config.auth.SecurityConfig;
import com.tsseo.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *  @RunWith
 *  테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴
 *  스프링 부트 테스트와 JUnit 사이에 연결자 역할
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class HelloControllerTest {

    // 스프링이 관리하는 빈(Bean)을 주입 받음
    @Autowired

    // Wep API를 테스트할 때 사용, 스프링 MVC 테스트의 시작점
    // 해당 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있음
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws  Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))           // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 함
                .andExpect(status().isOk())             // mvc.perform 의 결과를 검증
                                                        // HTTP Header 의 Status를 검증( ex. 200 400 500 등 )
                .andExpect(content().string(hello));    // 응답 본문의 내용을 검증
                                                        // Controller 에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name)                        // param 은 API 테스트할 때 사용될 요청 파라미터를 설정
                        .param("amount", String.valueOf(amount)))   // 단, 값은 String만 허용하므로 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경필요
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.name", is(name)))        // JSON 응닶값을 필드별로 검증할 수 있는 메소드
                            .andExpect(jsonPath("$.amount", is(amount)));   // $를 기준으로 필드명을 명시
    }
}
