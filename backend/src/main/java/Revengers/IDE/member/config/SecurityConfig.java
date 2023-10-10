package Revengers.IDE.member.config;

import Revengers.IDE.member.service.PrincipalDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PrincipalDetailsService memberDetailService;
    private static final int ONE_MONTH = 2678400; // 31일

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests( // 인가(접근 권한) 설정
                        (authz) -> authz.requestMatchers(new AntPathRequestMatcher("/admin/login")).permitAll()
                                .requestMatchers( new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/myaccount")).hasAnyRole("ADMIN", "MEMBER")
                                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                                .anyRequest().authenticated()
                ).formLogin( // 로그인 설정
                        (formLogin) -> formLogin.usernameParameter("memberId")
                                .passwordParameter("password")
                                .loginProcessingUrl("/api/member/login")
                                .loginPage("/login")
                                .successHandler(authenticationSuccessHandler())
                                .failureHandler(authenticationFailureHandler())
                                .permitAll()
                ).rememberMe(
                        (customizer) -> customizer.tokenValiditySeconds(ONE_MONTH)
                                .userDetailsService(memberDetailService)
                                .authenticationSuccessHandler(authenticationSuccessHandler())
                ).logout( // 로그아웃 설정
                        (logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/api/member/logout"))
                                .logoutSuccessHandler(logoutSuccessHandler())
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                ).sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionConcurrency((sessionConcurrency) ->
                                        sessionConcurrency
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(true)
                                ).sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 생성
                );

        return http.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
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
    @Component
    private static class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString("Spring: 로그인 성공")); // 원하는 JSON 응답 데이터를 설정

            String cookieValue = request.getSession().getId();
            response.addHeader("JSESSIONID", cookieValue);
            System.out.println(cookieValue);
        }
    }

    // 커스텀 로그인 성공 핸들러
    @Component
    private static class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString("Spring: 로그인 실패")); // 원하는 JSON 응답 데이터를 설정
        }
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * 로그아웃 성공 시 핸들러
     */
    @Component
    private static class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString("Spring: 로그아웃 성공"));
        }
    }
}
