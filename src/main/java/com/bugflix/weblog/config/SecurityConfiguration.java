package com.bugflix.weblog.config;

import com.bugflix.weblog.config.security.JwtAccessDeniedHandler;
import com.bugflix.weblog.config.security.JwtAuthenticationEntryPoint;
import com.bugflix.weblog.security.JwtAuthenticationFilter;
import com.bugflix.weblog.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] PERMIT_TO_USER = {
            "/api/v1/auths/test",
    };

    private static final String[] SWAGGER_URL_ARRAY = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };

    private static final String[] PERMIT_TO_ALL = {
            "/api/v1/users",
            "/api/v1/auths/login",
            "/api/v1/auths/reissue"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
                .httpBasic().disable()
                .csrf().disable()

                // CORS 설정
                .cors().configurationSource(corsConfigurationSource())

                // Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 조건별로 요청 허용/제한 설정
                .and()
                .authorizeRequests()

                .requestMatchers(HttpMethod.POST, "/api/v1/posts").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/posts").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/posts/**").hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/api/v1/posts/mine").hasRole("USER")

                // 조회
                .requestMatchers(HttpMethod.GET, "/api/v1/posts").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/posts/{postId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/posts/preview").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()

                .requestMatchers(HttpMethod.PATCH, "/api/v1/likes/**").hasRole("USER")

                .requestMatchers(PERMIT_TO_USER).hasRole("USER")
                .requestMatchers(PERMIT_TO_ALL).permitAll()
                .requestMatchers(SWAGGER_URL_ARRAY).permitAll()
                // FilterChain.doFilter(...) AccessDeniedException이 발생하던 이유는
                // denyAll 때문이 아닌 @PostMapping(value)가 아닌 @PostMapping(name)으로 잘못작성했기 때문이였음.
                .anyRequest().denyAll()

                // JWT 인증 필터 적용
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

                // 에러 핸들링
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /*
        CorsConfig - https://toycoms.tistory.com/37
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Cors 허용 패턴
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
