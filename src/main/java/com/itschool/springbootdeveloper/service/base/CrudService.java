package com.itschool.springbootdeveloper.service.base;

import com.itschool.springbootdeveloper.domain.base.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class CrudService <Req, Res, Entity extends BaseEntity> {

    protected final JpaRepository<Entity, Long> baseRepository;

    // 요청을 엔티티로 변경
    protected abstract Entity convertBaseEntityFromRequest(Req requestEntity);

    // 엔티티를 응답으로 변경
    protected abstract Res response(Entity entity);

    // 엔티티 리스트를 응답 리스트로 변경
    public List<Res> responseList(List<Entity> articleList) {
        return articleList.stream()
                .map((entity) -> response(entity))
                .toList();
    }

    // Create
    public Res create(Req request) {
        return response(baseRepository.save(convertBaseEntityFromRequest(request)));
    }

    // Read
    public Res read(Long id) {
        return response(baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음")));
    }

    public List<Res> readAll() {
        return responseList(baseRepository.findAll());
    }

    // Update
    @Transactional
    public Res update(Long id, Req request) {
        Entity entity = baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        entity.update(request);

        return response(entity);
    }

    public void delete(Long id) {
        Entity entity = baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        baseRepository.deleteById(id);
    }
}
