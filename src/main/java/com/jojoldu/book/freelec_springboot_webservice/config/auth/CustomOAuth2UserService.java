package com.jojoldu.book.freelec_springboot_webservice.config.auth;

import com.jojoldu.book.freelec_springboot_webservice.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.freelec_springboot_webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.freelec_springboot_webservice.domain.user.User;
import com.jojoldu.book.freelec_springboot_webservice.domain.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser (OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationID = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스 구분(google, naver ... )
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // primary key와 같은 의미

        OAuthAttributes attributes = OAuthAttributes.of(registrationID, userNameAttributeName, oAuth2User.getAttributes()); // 유저 정보를 OAuthAttribute 객체로 변환

        User user = saveOrUpdate(attributes); // DB에 저장 or 업데이트

        httpSession.setAttribute("user", new SessionUser(user)); // 세션에 사용자 정보를 저장

        return new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()); // 스프링 시큐리티에서 인증 객체로 사용됨
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
