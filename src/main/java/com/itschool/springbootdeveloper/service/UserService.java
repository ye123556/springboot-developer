package com.itschool.springbootdeveloper.service;

import com.itschool.springbootdeveloper.domain.User;
import com.itschool.springbootdeveloper.network.request.UserRequest;
import com.itschool.springbootdeveloper.network.response.MemberResponse;
import com.itschool.springbootdeveloper.network.response.UserResponse;
import com.itschool.springbootdeveloper.repository.UserRepository;
import com.itschool.springbootdeveloper.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends CrudService<UserRequest, UserResponse, User> {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository baseRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(baseRepository);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected UserResponse response(User entity) {
        return new UserResponse(entity);
    }

    @Override
    protected UserRepository getBaseRepository() {
        return (UserRepository) baseRepository;
    }

    @Override
    protected User convertBaseEntityFromRequest(UserRequest requestEntity) {
        return User.builder()
                .email(requestEntity.getEmail())
                .password(bCryptPasswordEncoder.encode(requestEntity.getPassword()))
                .build();
    }

    @Override
    public ResponseEntity<UserResponse> update(Long id, UserRequest request) {
        User entity = baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        entity.update(request);

        return ResponseEntity.ok(response(entity));
    }
}
