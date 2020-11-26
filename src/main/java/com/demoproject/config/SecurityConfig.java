package com.demoproject.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;




@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {

        // 시큐리티가 적용되지 않도록 경로를 직접 설정 가능
        web.ignoring().antMatchers("/resources/**","resources/**");
        // 부트가 제공하는 static resource 들에 대한 기본 위치에는 시큐리티가 적용되지 않도록 설정
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

        // 더블 슬래쉬 허용 설정(//) - 시큐리티는 더블슬래쉬를 허용하지 않음
        //web.httpFirewall(new DefaultHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf 설정 해제
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/admin/**").hasAuthority("ROLE_ADMIN")
                // 경로에 따른 권한요구 가능
                //.antMatchers("/", "/api/member/**").hasAuthority("ROLE_USER")
                //.antMatchers("/", "/api/board/**").hasAuthority("ROLE_USER")
                .and()
                    .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/loginProcess")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successForwardUrl("/member/loginCheck")
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 다양한 인코딩 타입을 지원하는 passwordencoder , boot 1.x 에서는 사용못함 (1.x 에서는 new BCryptPasswordEncoder(); 사용할 것)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
