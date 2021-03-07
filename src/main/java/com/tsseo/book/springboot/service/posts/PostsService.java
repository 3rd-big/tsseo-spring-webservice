package com.tsseo.book.springboot.service.posts;

import com.tsseo.book.springboot.posts.Posts;
import com.tsseo.book.springboot.posts.PostsRepository;
import com.tsseo.book.springboot.web.dto.PostsListResponseDto;
import com.tsseo.book.springboot.web.dto.PostsResponseDto;
import com.tsseo.book.springboot.web.dto.PostsSaveRequestDto;
import com.tsseo.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     *  (readOnly = true) 옵션을 주면 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선되기 때문에
     *  등록, 수정, 삭제 기능이 전혀 없는 서비스 메소드에서 사용함
     */
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                /**
                 *  .map(PostsListResponseDto::new) 해당 코드는 아래 코드와 동일
                 *  .map(posts -> new PostsListResponseDto(posts))
                 *  postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 PostsListResponseDto 변환 -> List로 반환하는 메소드
                 */
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        /**
         *  JpaRepository에서 이미 delete 메소드를 지원하고 있으므로 이를 활용
         *  엔티티를 파라미터로 삭제할 수도 있고, deleteById메소드를 이용하면 id로 삭제할 수도 있음
         *  존재하는 Posts인지 확인을 위해 엔티티 조회 후 그대로 삭제함
         */
        postsRepository.delete(posts);
    }
}
