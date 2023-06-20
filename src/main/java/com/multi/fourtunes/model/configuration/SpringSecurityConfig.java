package com.multi.fourtunes.model.configuration;


import com.multi.fourtunes.model.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Autor JMBAE
 */

@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

//    @Autowired
//    public SpringSecurityConfig(CustomUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

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

        http
                .authorizeRequests()
                // 인증되지 않은 사용자는, Index 까지만 접근 가능
                .antMatchers("/").permitAll()
                // 인증된 사용자는, 모든 페이지 접근 가능
                .antMatchers("/**").hasAuthority("USER")
                .and()
                // 로그인 페이지 지정
                .formLogin()
                    .permitAll()
                    .loginPage("/nav/login")
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
