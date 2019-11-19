package com.example.dome.application.config;

import com.example.dome.application.auth.UserAuthProvider;
import com.example.dome.application.auth.UserAuthorityVoter;
import com.example.dome.application.auth.UserRoleSecurityMetadataSource;
import com.example.dome.application.filter.TokenAuthFilter;
import com.example.dome.application.filter.UserLoginFilter;
import com.example.dome.application.handler.UserAccessDeniedHandler;
import com.example.dome.application.handler.UserLoginHandler;
import com.example.dome.application.handler.UserLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserLoginHandler userLoginHandler;

    @Autowired
    private UserLogoutHandler userLogoutHandler;

    @Autowired
    private UserAuthProvider userAuthProvider;

    @Autowired
    private UserAccessDeniedHandler jsonAccessDeniedHandler;

    @Autowired
    private TokenAuthFilter tokenAuthFilter;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //禁用SESSION
                .httpBasic()
                .authenticationEntryPoint(jsonAccessDeniedHandler).and()
                .exceptionHandling().accessDeniedHandler(jsonAccessDeniedHandler).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/signUp").permitAll()
                .antMatchers("/other/**").permitAll()
                .anyRequest().authenticated().accessDecisionManager(accessDecisionManager()).and()
                .logout().logoutUrl("/user/logout").addLogoutHandler(userLogoutHandler).logoutSuccessHandler(userLogoutHandler);

        http.addFilterAt(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(userLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore()
    }

    public UserLoginFilter userLoginFilter() throws Exception {
        UserLoginFilter filter = new UserLoginFilter("/user/login");
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(userLoginHandler);
        filter.setAuthenticationFailureHandler(userLoginHandler);
        return filter;
    }

    private AccessDecisionManager accessDecisionManager() {
        return new UnanimousBased(Arrays.asList(new WebExpressionVoter(), new UserAuthorityVoter()));
    }

}
