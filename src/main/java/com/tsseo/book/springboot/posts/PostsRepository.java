package com.tsseo.book.springboot.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    /**
     *  @Query
     *  SpringDataJpa에서 제공하지 않는 메소드는 아래처럼 쿼리로 작성해도 됨
     *  실제로 아래의 코드는 SpringDataJpa에서 제공하는 기본 메소드만으로 해결 할 수 있지만, @Query가 훨씬 가독성이 좋음
     *  
     *  규모가 있는 프로젝트에서의 데이터 조회는 FK의 조인, 복잡한 조건 등으로 인해 이런 Entity클래스만으로 처리하기 어려워 
     *  조회용 프레임워크를 추가로 사용함. 대표적인 예로 querydsl, jooq, MyBatis 등이 있음.
     *  조회는 위 3가지 프레임워크 중 하나를 통해 조회하고, 등록/수정/삭제 등은 SpringDataJpa를 통해 진행
     */
    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();
}
