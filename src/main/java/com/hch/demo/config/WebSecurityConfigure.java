package com.hch.demo.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Slf4j
@Configuration
@EnableWebSecurity
/**
 * extends WebSecurityConfigurerAdapter 상속(구버전) => 메소드에서 SecurityFilterChain 반환
 */
public class WebSecurityConfigure {

    /**
     * 스프링 시큐리티 커스텀 설정
     * 구버전 : configure(HttpSecurity http) => SecurityFilterChain filterChain(HttpSecurity http)
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**", "/favicon.ico", "/error").permitAll()        // 정적자원 모든사용자 사용 가능
                .antMatchers("/", "/users", "/users/**").permitAll()                    // users API 모든사용자 사용 가능
                .antMatchers("/v", "/v/**").access("hasRole('ROLE_VIEW')")      // /v, /v/** ROLE_VIEW 권한을 가진 사용자만 접근가능
                .anyRequest().authenticated()                                                       // 설정되지 않은 모든 URL은 인가된 사용자만 이용 가능
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/v").permitAll()                // 로그인페이지는 /login 이고 로그인 성공시 /v 로 이동
                .usernameParameter("username").passwordParameter("password")                        // id, password 파라미터 명 설정(로그인 페이지에도 동일하게 설정)
                .and()
                .logout().permitAll()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());         // csrf 공격방지를 위해 설정, post 요청시 반드시 _csrf token 값을 넣어줘야함(헤더에 X-XSRF-TOKEN 추가)

        return http.build();
    }

//    /**
//     * WebSecurity 설정
//     * 구버전 : configure(WebSecurity web) => WebSecurityCustomizer webSecurityCustomizer()
//     * @return
//     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // 정적자원 인증/인가 무시
//        // /favicon.ico, /error 도 추가(안넣어주면 로그인 성공 시 이동하는 페이지의 정적 자원중 일부를 못읽어 들여서 오류가 나게됨)
//        // 로그에 permitAll 로 사용을 추천한다고 떠서 fileterChain 메소드에 추가함
//        return (web) -> web.ignoring().antMatchers("/static/**", "/favicon.ico", "/error");
//    }
}
