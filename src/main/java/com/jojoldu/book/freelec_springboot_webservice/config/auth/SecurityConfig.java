package com.jojoldu.book.freelec_springboot_webservice.config.auth;

import com.jojoldu.book.freelec_springboot_webservice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 (REST API 기반 서비스에서는 CSRF 보호가 필요 없음)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // h2 console을 사용하기 위해선, X-Frame-Options를 비활성화해야함
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() // 이렇게 설정된 url에는 모든 사람이 접근 가능
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER role을 가진 사람만 접근 가능
                        .anyRequest().authenticated() // 로그인 한 사람만 접근 가능
                )
                .logout(logout -> logout.logoutSuccessUrl("/")) // 로그아웃 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)) // 로그인 처리 및 사용자 정보 조회
                        .defaultSuccessUrl("/", true)
                );

        return http.build();
    }
}