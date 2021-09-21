package ru.netology.safeapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class AppSecure extends WebSecurityConfigurerAdapter {

    private final String[] userNames = new String[] {"Andrey", "Alexey"};
    private final String[] passes = new String[]{"qwerty1234", "qwerty"};
    private final String[] userRoles = new String[]{"ADMIN","USER"};
    private final int adminIdx = 0;
    private final int userIdx = 1;

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        managerBuilder.inMemoryAuthentication()
                        .withUser(userNames[adminIdx])
                        .password(passwordEncoder.encode(passes[adminIdx]))
                        .roles(userRoles[adminIdx])
                       .and()
                        .withUser(userNames[userIdx])
                        .password(passwordEncoder.encode(passes[userIdx]))
                        .roles(userRoles[userIdx]);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/index").permitAll()
                .and()
                .authorizeRequests().antMatchers("/admin").hasAuthority("ROLE_" + userRoles[adminIdx])
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin();
    }
}
