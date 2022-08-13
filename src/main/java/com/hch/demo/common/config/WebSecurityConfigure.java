package com.hch.demo.common.config;


import com.hch.demo.common.handler.WebAccessDeniedHandler;
import com.hch.demo.service.SecurityUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Slf4j
@Configuration
@AllArgsConstructor
@EnableWebSecurity
/**
 * extends WebSecurityConfigurerAdapter 상속(구버전) => 메소드에서 SecurityFilterChain 반환
 */
public class WebSecurityConfigure {

    // 유저 시큐리티 서비스
    private final SecurityUserService securityUserService;

    // 접근권한 핸들러
    private final WebAccessDeniedHandler webAccessDeniedHandler;

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
                //@ 접근 권한설정
                .antMatchers("/static/**", "/favicon.ico", "/error").permitAll()    // 정적자원 모든사용자 사용 가능
                .antMatchers("/", "/swagger-ui/**", "/token", "/api-docs/**", "/api/v1/**").permitAll()       // API
                .antMatchers("/login", "/join").permitAll()                         // 로그인, 회원가입 페이지
                .antMatchers("/v/users").hasRole("ADMIN")                           // ROLE_ADMIN 권한을 가진 사용자만 접근 가능
                .antMatchers("/v", "/v/**").hasRole("VIEW")                         // ROLE_VIEW 권한을 가진 사용자만 접근 가능
                .anyRequest().authenticated()                                                   // 설정되지 않은 모든 URL은 인가된 사용자만 이용 가능
                //@ 로그인페이지 설정
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/v").permitAll()            // 로그인페이지는 /login 이고 로그인 성공시 /v 로 이동
                .usernameParameter("email").passwordParameter("password")                       // email, password 파라미터 명 설정(로그인 페이지에도 동일하게 설정)
                //@ 로그아웃 설정
                .and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                //@ 접근권한에러 핸들러 설정
                .and().exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
                //@ 커스텀 authenticationProvider 설정(유저권한정보) => 로그인 성공시 SecurityUser 를 반환하는 기능을 정의한 securityUserService를 여기에 정의
                .and().authenticationProvider(authenticationProvider())
                //@ csrf 공격방지 설정
                .csrf()
                .requireCsrfProtectionMatcher(new CsrfRequireMatcher())                 // csrf 체크 제외(예외)
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());    // csrf 공격방지를 위해 설정, post 요청시 반드시 _csrf token 값을 넣어줘야함(헤더에 X-XSRF-TOKEN 추가)

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityUserService);
        authProvider.setPasswordEncoder(passwordEncoder());     // 비밀번호 암호화 설정
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
