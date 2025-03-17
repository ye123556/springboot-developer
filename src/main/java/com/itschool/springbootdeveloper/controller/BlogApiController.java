package com.itschool.springbootdeveloper.controller;

import com.itschool.springbootdeveloper.controller.base.CrudController;
import com.itschool.springbootdeveloper.domain.Article;
import com.itschool.springbootdeveloper.network.request.ArticleRequest;
import com.itschool.springbootdeveloper.network.response.ArticleResponse;
import com.itschool.springbootdeveloper.service.BlogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("/api/articles")
@Tag(name = "게시글", description = "게시글 관련 API")
public class BlogApiController extends CrudController<ArticleRequest, ArticleResponse, Article> {
    @Autowired
    public BlogApiController(BlogService baseService) { // BlogService
        super(baseService);
    }

    protected BlogService getBaseService() {
        return (BlogService) baseService;
    }
}
