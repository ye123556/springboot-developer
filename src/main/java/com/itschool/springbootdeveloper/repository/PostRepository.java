package com.itschool.springbootdeveloper.repository;

import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findById(Integer post_id);
    List<Post> findByContent(String content);

}
