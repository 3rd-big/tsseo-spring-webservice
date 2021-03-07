package com.tsseo.book.springboot.web;

import com.tsseo.book.springboot.service.posts.PostsService;
import com.tsseo.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    /**
     *  머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨
     *  앞의 경로는 src/main/resources/templates
     *  뒤의 파일 확장자는 .mustache가 붙음
     *  여기선 "index"를 반환하므로, src/main/resources/templates/index.mustache로 전환되어 View Resolver가 처리하게 됨
     */
    @GetMapping("/")
    /**
     *  Model
     *  서버 템플릿 엔진에서 사용할 수 있는 객체를 저장함
     *  여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달함
     */
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
