package com.ssafy.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// test 링크로 연결했습니다 front와 연동 이후 링크만 수정 하면됩니다
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
        		"/",
                "/v2/api-docs", "/configuration/ui", "/swagger-resoureces",
                "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.cors()
        .and() // Session 전략 설정
            .csrf().disable()  // axios post 요청시 csrf token이 없으면 요청이 거절당한다. 일시적으로 사용안함으로 바꿈.
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and()  // 페이지 권한 설정
            .authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/static/**").permitAll()
            .antMatchers("/search/**",  "/**").permitAll()
            .antMatchers("/signup/**").authenticated()
//            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
//        .and() // 로그인 설정 
//            .formLogin() 
//            .loginPage("/user/auth/login")
//            .defaultSuccessUrl("/")
//            .permitAll()
        .and() // 로그아웃 설정
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
            .logoutSuccessUrl("/user/auth/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        .and() // 에러 핸들링
            .exceptionHandling().accessDeniedPage("/admin/error");

}
    
}
