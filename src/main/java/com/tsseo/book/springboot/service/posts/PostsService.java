package com.tsseo.book.springboot.service.posts;

import com.tsseo.book.springboot.posts.Posts;
import com.tsseo.book.springboot.posts.PostsRepository;
import com.tsseo.book.springboot.web.dto.PostsResponseDto;
import com.tsseo.book.springboot.web.dto.PostsSaveRequestDto;
import com.tsseo.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private  final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    /**
     *  update 기능에서 테이터베이스에 쿼리를 날리는 부분이 없음.
     *  이게 가능한 이유는 JPA의 영속성 컨텍스트(엔티티를 영구 저장하는 환경) 때문
     *  JPA의 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 데이터베이스에서 테이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태
     *  이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영함.
     *  Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없음
     *  해당 개념을 더티 체킹(dirty checking) 이라고 함
     */
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
