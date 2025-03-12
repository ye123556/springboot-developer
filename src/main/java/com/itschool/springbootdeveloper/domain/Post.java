package com.itschool.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "post_id", updatable = false)
    private Integer post_id;

    @Column(nullable = false, columnDefinition = "VARCHAR(200) DEFAULT ''")
    private String content;

    public Post(String content) {
        this.content = content;
    }

}
