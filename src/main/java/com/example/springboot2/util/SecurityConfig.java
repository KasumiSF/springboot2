package com.example.springboot2.util;

import com.example.springboot2.server.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.
        builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.
        HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.
        EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.
        WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.
        UsernameNotFoundException;

/**
 * Spring Security配置
 * 自定义的安全配置类
 * 让Spring Boot跳过了安全自动配置，转而使用我们的安全配置
 *
 * 想要覆盖Spring Boot的自动配置，你所要做的仅仅是编写一个显式的配置。
 * Spring Boot会发现你的配置，随后降低自动配置的优先级，以你的配置为准。
 *
 * 覆盖Spring Boot自动配置的安全配置时，最重要的一个类是 SpringBootWebSecurityConfiguration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ReaderRepository readerRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").access("hasRole('READER')")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true");
    }
    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(new UserDetailsService() {
                    @Override
                    public UserDetails loadUserByUsername(String username)
                            throws UsernameNotFoundException {
                        return readerRepository.findOne(username);
                    }
                });
    }
}