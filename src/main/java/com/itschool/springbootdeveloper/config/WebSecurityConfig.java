package com.itschool.springbootdeveloper.config;

import com.itschool.springbootdeveloper.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailService userDetailService) throws Exception{
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService); // 사용자 정보 서비스 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return new ProviderManager(authProvider);
    }

    // 스프링 시큐리티 기능 비활성화 : 인증과 인가를 적용하지 않는 곳 명시
    @Bean
    public WebSecurityCustomizer confiure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console(),
                        new AntPathRequestMatcher("/js/**"),
                        new AntPathRequestMatcher("/css/**"),

                        // 개발 단계여서 api test를 위해 열었음. 운영에 반영되면 절대 안됨!
                        new AntPathRequestMatcher("/api/**"),
                        new AntPathRequestMatcher("/api-docs"),
                        new AntPathRequestMatcher("/api-docs/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/swagger*/**"),
                        new AntPathRequestMatcher("/swagger-resources/**")
                );
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth // 인증, 인가 설정
                        .requestMatchers( // 인증이 없어도 접속할 수 있는 URL
                                new AntPathRequestMatcher("/login"), // 로그인
                                new AntPathRequestMatcher("/signup"), // 회원 가입
                                new AntPathRequestMatcher("/user"), // 유저 생성
                                new AntPathRequestMatcher("/api/user/check-email"), // 이메일 중복 체크
                                new AntPathRequestMatcher("/api/user/phonenumber"), // 핸드폰 중복 체크
                                new AntPathRequestMatcher("/api/**") // 인증이 없어도 swagger api 테스트할 수 있게 함. 운영에는 절대 반영 x
                        ).permitAll() // 해당 안에 url은 전부 허용하고
                        .anyRequest().authenticated()) // 나머지 요청은 인증이 되어야 접근이 가능하다
                .formLogin(formLogin -> formLogin // 폼 기반 로그인 설정
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .build();
    }
}
