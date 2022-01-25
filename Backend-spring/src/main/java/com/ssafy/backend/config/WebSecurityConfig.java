package com.ssafy.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// test 링크로 연결했습니다 front와 연동 이후 링크만 수정 하면됩니다
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    	http.authorizeRequests()
                .antMatchers(
                		"/home",
                		"/search/**",
                		"/**", // test용으로 모든 url허가함, front 연동 이후 삭제
                		//swagger 설정
                		"/swagger-ui.html/**",
                		"/configuration/**",
                		"/swagger-resources/**",
                		"/v2/api-docs",
                		"/webjars/**",
                		"/webjars/springfox-swagger-ui/*.{js,css}").permitAll() // 해당 링크는 인증 안한 사용자들 모두에게 보여줘라
                .anyRequest().authenticated() //나머지 모든 요청들은 인증이 필요하다
                .and()
            .formLogin()
                .loginPage("/login") // 로그인 페이지로 가는 요청이 무엇이냐?
                .defaultSuccessUrl("/")
                .permitAll()    // 모든 사용자가 접근가능하도록 설정
                .and()
            .logout()
            	.logoutSuccessUrl("/home")
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}