package com.itschool.springbootdeveloper.service;

import com.itschool.springbootdeveloper.domain.Post;
import com.itschool.springbootdeveloper.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired // 빈 주입
    PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll(); // 멤버 목록 얻기
    }

    public Post findPostById(Integer post_id) {
        return postRepository.findById(post_id) // 찾으면 Member 객체 리턴
                .orElseThrow(() -> new EntityNotFoundException()); // 못 찾으면 예외 발생
    }

    public List<Post> findPostsByContent(String content) {
        return postRepository.findByContent(content);
    }

    @Transactional // 트랜잭션 처리
    public void test() {


        // 1. 생성
        Post post = new Post("A");
        postRepository.save(post); // 기존에 존재하는지 확인, 없으면 Create, 있으면 Update
        // IDENTITY 전략은 실행 시점에 즉시 INSERT SQL이 실행됨, 성능 상의 이점을 볼 수 없음

        // 2. 조회
        Optional<Post> post2 = postRepository.findById(1); // 단건 조회
        List<Post> allPost = postRepository.findAll();

        // 3. 삭제
        postRepository.deleteById(1);
    }
}
