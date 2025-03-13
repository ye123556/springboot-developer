package com.itschool.springbootdeveloper.controller.base;

import com.itschool.springbootdeveloper.network.request.ArticleRequest;
import com.itschool.springbootdeveloper.network.response.ArticleResponse;
import com.itschool.springbootdeveloper.service.BlogService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CrudController <Req, Res Entity> {
    protected final BaseService<Req, Res, Entity> blogService;

    @GetMapping("{id}")
    public ResponseEntity<Res> read(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.read(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Res>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.readAll());
    }

    @PostMapping("")
    public ResponseEntity<Res> create(@RequestBody Res request) { // HttpSercletRequest request    throw IO~
        // 200 OK, 201 Created, 400 Bad Request...                                       // ObjectNapper objectMapper = new ObjectMapper();
        return ResponseEntity.status(HttpStatus.CREATED)                                 // ServletInputStream sis = request.getInputStream();
                .body(blogService.create(request));                                      // ArticleRequest requestData  = objectMapper.readValue(sis, ArticleRequest.class);// 다 못적임 예전방식
    }                                                                                   // ArticleResponse response = blogServer.create(requestData);
    // ?   그다음 return Response
    // update http method? put(전체 수정), patch(부분 수정), patch는 이 과정에서는 생략
    @PutMapping("{id}")
    public ResponseEntity<ArticleResponse> update(@PathVariable Long id,
                                                  @RequestBody ArticleRequest request) {
        ArticleResponse response = blogService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    // delete http method? delete(단건 삭제)
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        blogService.delete(id); // return 값 확인
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
