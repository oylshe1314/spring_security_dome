package com.example.dome.application.config;

import com.example.dome.application.filter.RestLoginFilter;
import com.example.dome.application.handler.RestAccessDeniedHandler;
import com.example.dome.application.handler.RestLoginHandler;
import com.example.dome.application.handler.RestLogoutHandler;
import com.example.dome.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RestLoginHandler restLoginHandler;

    @Autowired
    RestLogoutHandler restLogoutHandler;

    @Autowired
    RestAccessDeniedHandler jsonAccessDeniedHandler;

    @Autowired
    UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().authenticationEntryPoint(jsonAccessDeniedHandler).and()
                .exceptionHandling().accessDeniedHandler(jsonAccessDeniedHandler).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/sign-up").permitAll()
                .anyRequest().authenticated().and()
                .logout().logoutUrl("/user/logout").addLogoutHandler(restLogoutHandler).logoutSuccessHandler(restLogoutHandler);

        http.addFilterAt(restLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public RestLoginFilter restLoginFilter() throws Exception {
        RestLoginFilter filter = new RestLoginFilter();
        filter.setFilterProcessesUrl("/user/login");
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(restLoginHandler);
        filter.setAuthenticationFailureHandler(restLoginHandler);
        return filter;
    }

}
