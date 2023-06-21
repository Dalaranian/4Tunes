package com.multi.fourtunes.model.configuration;

import com.multi.fourtunes.model.security.CustomAuthenticationSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Autor JMBAE
 */

@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;


    /**
     * @param auth
     * @throws Exception
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 파일 필터링 방지
        return (web) -> web.ignoring().antMatchers("/resources/css/**", "/resources/js/**", "/resources/img/**").requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                // 인증되지 않은 사용자는, Index 까지만 접근 가능
                .antMatchers("/", "/favicon.ico", "/search/query", "/login/**").permitAll()
                // 인증된 사용자는, 모든 페이지 접근 가능
                .antMatchers("/**").hasAuthority("USER")
                .and()
                // 로그인 페이지 지정
                .formLogin()
                    .permitAll()
                    .loginPage("/nav/login")
                    .defaultSuccessUrl("/")
                    .failureUrl("/nav/login")
                    .usernameParameter("user_id")
                    .passwordParameter("user_pw")
                    .loginProcessingUrl("/security/login")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(
                            new AuthenticationFailureHandler() {
                                @Override
                                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                    System.out.println("exception : " + exception.getMessage());
                                    response.sendRedirect("/nav/login");
                                }
                            }
                    )
                    .and()
                // 로그아웃 세팅
                .logout()
                    .permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))
                    .deleteCookies("JESSIONID")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true);

        return http.build();
    }

    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
