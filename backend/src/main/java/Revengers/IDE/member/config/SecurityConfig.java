package Revengers.IDE.member.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable) // 사이트 위변조 요청 방지
                .authorizeHttpRequests( // 인가(접근 권한) 설정
                        (authz) -> authz.requestMatchers(
                                        new AntPathRequestMatcher("/admin/**")
                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        new AntPathRequestMatcher("/myaccount")
                                ).hasAnyRole("ADMIN", "MEMBER")
                                .requestMatchers(
                                        new AntPathRequestMatcher("/**")
                                ).permitAll()
                                .anyRequest().authenticated()
                ).formLogin( // 로그인 설정
                        (formLogin) -> formLogin.usernameParameter("memberId")
                                .passwordParameter("password")
                                .loginProcessingUrl("/api/member/login")
                                .loginPage("/login")
                                .defaultSuccessUrl("/api/member/loginSuccess") // 로그인 성공
                                .failureUrl("/api/member/loginFailed") // 로그인 실패
                ).logout( // 로그아웃 설정
                        (logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/api/member/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                ).sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionConcurrency((sessionConcurrency) ->
                                        sessionConcurrency
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(true)
                                )
                );

        return http.build();
    }

}
