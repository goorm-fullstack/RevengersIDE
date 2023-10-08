package Revengers.IDE.member.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

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
                                //.loginPage("/login")
                                .successHandler(authenticationSuccessHandler())
                                .failureHandler(authenticationFailureHandler())
//                                .defaultSuccessUrl("/api/member/loginSuccess") // 로그인 성공
//                                .failureUrl("/api/member/loginFailed") // 로그인 실패
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


    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    // 커스텀 로그인 성공 핸들러
    private static class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString("로그인 성공")); // 원하는 JSON 응답 데이터를 설정
        }
    }

    // 커스텀 로그인 성공 핸들러
    private static class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString("로그인 실패")); // 원하는 JSON 응답 데이터를 설정
        }
    }
}
