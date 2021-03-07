package com.tsseo.book.springboot.web;

import com.tsseo.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @RestController
 *  JSON을 반환하는 컨트롤러로 만들어 줌
 *  예전에는 @ResponseBody 를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해줌
 */
@RestController
public class HelloController {
    
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    /**
     * @RequestParam
     * 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
     */
    public HelloResponseDto HelloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
